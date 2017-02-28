package com.systemc.main;

import java.util.List;

import com.systemc.controller.MainController;
import com.systemc.model.Module;
import com.systemc.util.FileUtil;

public class Main {

	private MainController mainCtrl = new MainController();

	public static void main(String[] args) {
		new Main().startParsing();
	}

	private void startParsing() {
		List<String> lines = FileUtil.readFile("input.txt");

		if (lines != null) {
			List<Module> modules = mainCtrl.getModules(lines);

			if (modules != null) {

				for (Module module : modules) {
					System.out.println(module.toString());
				}
			}
		}
	}

}
