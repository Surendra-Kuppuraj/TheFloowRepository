/**
 * 
 */
package com.thefloow.wordcountapp;

import java.io.IOException;
import java.util.Map;

import com.thefloow.service.WordCountProcessorService;
import com.thefloow.service.WordCountProcessorServiceImpl;

/**
 * @author surendrakuppuraj
 *
 */
public class WordCountAppImpl implements WordCountApp {

	private final WordCountProcessorService wordCountService;

	public WordCountAppImpl(final String hostName) {
		wordCountService = new WordCountProcessorServiceImpl(hostName);
	}

	@Override
	public void createWordCount(final String file) throws IOException {
		wordCountService.createWordCount(file);
	}

	@Override
	public Map<String, Integer> getWordCount() {
		return wordCountService.getGeneratedWordCount();
	}

}
