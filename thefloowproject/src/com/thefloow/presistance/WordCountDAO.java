package com.thefloow.presistance;

/**
 * @author surendrakuppuraj
 *
 */
public interface WordCountDAO<T> {

	public void create(T wordCountMap);
	
	public T getAll();

	
}
