package com.thefloow.wordcountapp;

import java.io.IOException;
import java.util.Map;

public interface WordCountApp {
	void createWordCount(final String file) throws IllegalArgumentException, IOException;
	Map<String, Integer> getWordCount();
}
