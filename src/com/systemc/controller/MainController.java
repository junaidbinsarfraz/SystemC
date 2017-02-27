package com.systemc.controller;

import java.util.ArrayList;
import java.util.List;

import com.systemc.model.Module;
import com.systemc.util.StringUtil;

public class MainController {
	
	private List<Module> modules;
	private Module currentModule;
	private enum Scope {MODULE, METHOD, MODULE_METHOD, GLOBAL};
	private enum Keywords {SC_MODULE, SC_IN, SC_OUT, SC_INOUT, SC_PORT, SC_FIFO_IN, SC_FIFO_OUT, SC_CLK_IN};
	private enum Other {};
	
	private Scope currentScope;
	
	public List<Module> getModules(List<String> lines) {
		
		currentScope = Scope.GLOBAL;
		
		modules = new ArrayList<Module>();
		
		for(String line : lines) {
			line = StringUtil.trim(line);
			
			if(currentScope.equals(Scope.METHOD)) {
				// Check end of method
				
				
				
				// else ignore
			} else if(currentScope.equals(Scope.MODULE)) {
				// Check for port name
				
				// else check for method start
				
				// else check end of module
				
				// else ignore
			} else if(currentScope.equals(Scope.MODULE_METHOD)) {
				// Check end of method
				
				// else ignore
			} else if(currentScope.equals(Scope.GLOBAL)) {
				// Check for module
				
				// If no module the check for method
				
				// else ignore
			} 
			
			
			
		}
		
		return modules;
	}
	
}
