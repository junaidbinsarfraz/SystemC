package com.systemc.util;

public class StringUtil {
	
	public static String trim(String str) {
		
		str = str.replace("\t", "");
		str = str.trim().replaceAll(" +", " ");
		
		return str.trim();
		
	}
	
}
