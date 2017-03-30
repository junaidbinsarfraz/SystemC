package com.systemc.util;

/**
 * The class StringUtil is use to manipulate the strings
 */
public class StringUtil {

	public static String trim(String str) {

		str = str.replace("\t", "");
		str = str.trim().replaceAll(" +", " ");

		return str.trim();

	}

	public static String getSubString(String parent, String delimator1, String delimator2) {
		return parent.substring(parent.indexOf(delimator1) + 1, parent.indexOf(delimator2));
	}

	public static String getSubStringBetweenSameChar(String parent, String delimator1, String delimator2) {
		return parent.substring(parent.indexOf(delimator1) + 1, parent.lastIndexOf(delimator2));
	}

	public static Boolean doesExists(String parent, String child) {
		return parent.toLowerCase().contains(child.toLowerCase());
	}

}
