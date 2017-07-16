/**
 * 
 */
package com.thefloow.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import com.thefloow.presistance.WordCountDAO;
import com.thefloow.presistance.WordCountDAOImpl;
import com.thefloow.utils.Stopwords;
import com.thefloow.utils.StringUtils;

/**
 * @author surendrakuppuraj
 *
 */
public class WordCountProcessorServiceImpl implements WordCountProcessorService {

	private static final String SEARCH_REGEX = "[^A-Za-z]+";
	private static final String WHITESPACE = "\\s";
	private final WordCountDAO<Map<String, Integer>> wordcountDAO;

	public WordCountProcessorServiceImpl(final String hostName) {
		wordcountDAO = new WordCountDAOImpl(hostName);
	}

	@Override
	public void createWordCount(String xmlFile) throws IOException {

		Map<String, Integer> countedWordMap = filePostProcess(filePreProcess(xmlFile));
		synchronized(countedWordMap){
			wordcountDAO.create(countedWordMap);
		}
	}

	@Override
	public Map<String, Integer> getGeneratedWordCount() {
		synchronized(this){
			return wordcountDAO.getAll();
		}		
	}

	private StringBuffer filePreProcess(final String file) throws IOException {

		if (!Files.exists(Paths.get(file))) {
			throw new FileNotFoundException(" Given file does not exits!. ");
		}

		StringBuffer bufferedText = new StringBuffer();
		try (BufferedReader buffreader = new BufferedReader(new FileReader(file))) {
			String word;
			synchronized (bufferedText) {
				while ((word = buffreader.readLine()) != null) {
					bufferedText.append(word.replaceAll(SEARCH_REGEX, " ").toLowerCase());
				}
			}
		}
		return bufferedText;
	}

	private Map<String, Integer> filePostProcess(final StringBuffer text) throws IllegalArgumentException {

		if (StringUtils.isEmpty(text)) {
			throw new IllegalArgumentException(" The text is not in acceptable way for post processing!. ");
		}

		Collection<String> scannedList = new LinkedList<>();

		synchronized (scannedList) {
			try (Scanner s = new Scanner(text.toString())) {
				s.useDelimiter(WHITESPACE);
				while (s.hasNext()) {
					scannedList.add(s.next());
				}
			}
			scannedList.removeAll(new Stopwords().getStopWordsList());
		}
		final Integer INCREMENTAL_NUMBER = 1;
		Collection<String> countList = new ArrayList<String>(scannedList);
		Map<String, Integer> resultMap = new HashMap<>();
		synchronized (resultMap) {
			for (String word : countList) {
				if (!resultMap.containsKey(word)) {
					resultMap.put(word, INCREMENTAL_NUMBER);
				} else {
					resultMap.put(word, resultMap.get(word) + INCREMENTAL_NUMBER);
				}	
			}
		}
		return resultMap;
	}

}
