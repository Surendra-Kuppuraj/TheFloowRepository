package com.thefloow.presistance;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;

/**
 * @author surendrakuppuraj
 *
 */
public interface WordCountDAO<T> {

	public void create(T wordCountMap);
	
	public T getAll();

	
}
