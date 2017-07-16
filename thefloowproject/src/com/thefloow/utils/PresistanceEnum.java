/**
 * 
 */
package com.thefloow.utils;

/**
 * @author surendrakuppuraj
 *
 */
public enum PresistanceEnum {
	DATABASENAME ("THEFLOOWDB"), TABLE_NAME("WordCount");
	
	private String value;
	PresistanceEnum(String value){
		this.value = value;
	}
	
}
