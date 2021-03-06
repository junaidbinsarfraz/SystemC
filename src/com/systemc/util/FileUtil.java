package com.systemc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class FileUtil is use to read the file
 */
public class FileUtil {

	/**
	 * The method readFile() is use to read file and return all the lines
	 * 
	 * @param name
	 *            filename to be read
	 * @return list of lines
	 */
	public static List<String> readFile(String name) {

		// To store each line from file
		List<String> lines = new ArrayList<String>();

		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(name);
			br = new BufferedReader(fr);

			String line;

			br = new BufferedReader(new FileReader(name));

			// while not end-of-file, add line in lines list
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}

		} catch (IOException e) {
			System.err.println("Unable to read file");
		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return lines;

	}

}
