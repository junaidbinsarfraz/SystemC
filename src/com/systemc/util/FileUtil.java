package com.systemc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static List<String> readFile(String name) {

		List<String> lines = new ArrayList<String>();

		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(name);
			br = new BufferedReader(fr);

			String line;

			br = new BufferedReader(new FileReader(name));

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
