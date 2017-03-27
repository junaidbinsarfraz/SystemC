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

	private void startParsing() {
		List<String> lines = FileUtil.readFile("input.txt");

		if (lines != null) {
			List<ModuleSignature> modules = mainCtrl.getModules(lines);
			
			System.out.println("------------------- Step1 -------------------\n\n");
			
			if (modules != null) {

				for (ModuleSignature module : modules) {
					System.out.println(module.toString());
				}
			}
			
			List<Signal> signals = mainCtrl.getSignals(lines, modules);
			
			System.out.println("------------------- Step2 -------------------\n\n");
			
			if (signals != null) {

				for (Signal signal : signals) {
					System.out.println(signal.toString());
				}
			}
		}
	}

}
