/**
 * 
 */
package com.thefloow.service;

import java.io.IOException;
import java.util.Map;

/**
 * @author surendrakuppuraj
 *
 */
public interface WordCountProcessorService {

	void createWordCount(final String xmlFile) throws IOException;
	Map<String, Integer> getGeneratedWordCount();

}
