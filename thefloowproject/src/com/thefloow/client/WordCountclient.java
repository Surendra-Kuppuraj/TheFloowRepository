package com.thefloow.client;

import java.io.IOException;

import com.thefloow.wordcountapp.WordCountApp;
import com.thefloow.wordcountapp.WordCountAppImpl;

public class WordCountclient {

	public static void main(String[] args) {
		if(args.length != 2){
			throw new IllegalArgumentException ("Please enter valid path as follows: java –Xmx8192m -jar challenge.jar dump.xml hostname:port");
		}
		
		try {
			WordCountApp wordcountapp = new WordCountAppImpl(args[1]);
			wordcountapp.createWordCount(args[0]);
			System.out.println(wordcountapp.getWordCount());
		} catch (IllegalArgumentException | IOException e) {
			System.err.println(e);
		}
		
	}

}
