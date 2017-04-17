package com.systemc.main;

import java.util.List;

import com.systemc.controller.MainController;
import com.systemc.model.ModuleSignature;
import com.systemc.model.Signal;
import com.systemc.util.FileUtil;

public class Main {

	private MainController mainCtrl = new MainController();

	public static void main(String[] args) {
		new Main().startParsing();
	}

	/**
	 * The method startParsing() is use to start the program to read file, parse
	 * lines and display the result
	 */
	private void startParsing() {
		// Read lines from the file. 
		List<String> lines = FileUtil.readFile("input.txt");

		// Check if lines are null or not so that it can be parsed.
		if (lines != null) {
			// Step1 -> Get Modules (name and their ports). It will also helpful for step2
			List<ModuleSignature> modules = mainCtrl.getModules(lines);

			System.out.println("------------------- Step1 -------------------\n\n");

			// Check if any modules are found or not
			if (modules != null) {
				// Print all the modules name and respective ports name
				for (ModuleSignature module : modules) {
					System.out.println(module.toString());
				}
			}

			// Step2 -> Get Signals. In this method we will detect module instances, signal instances and modules' ports connection via signal instances
			List<Signal> signals = mainCtrl.getSignals(lines, modules);

			System.out.println("------------------- Step2 -------------------\n\n");

			// Check if any signal found
			if (signals != null) {

				// Print all the module instances, signal instances and modules' ports connection via signal instances
				for (Signal signal : signals) {
					System.out.println(signal.toString());
				}
			}
		}
	}

}
