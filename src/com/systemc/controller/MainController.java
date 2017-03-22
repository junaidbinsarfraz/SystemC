package com.systemc.controller;

import java.util.ArrayList;
import java.util.List;

import com.systemc.model.ModuleSignature;
import com.systemc.model.Port;
import com.systemc.model.Signal;
import com.systemc.util.StringUtil;

public class MainController {

	private List<ModuleSignature> moduleSignatures;
	private ModuleSignature currentModuleSignature;
	private List<Signal> signals;

	private enum Scope {
		MODULE, METHOD, MODULE_METHOD, GLOBAL
	};

	private enum Keyword {
		SC_MODULE, SC_MAIN, MAIN
	};

	private enum PortType {
		SC_IN, SC_OUT, SC_INOUT, SC_PORT, SC_FIFO_IN, SC_FIFO_OUT, SC_CLK_IN
	};
	
	private enum SignalType {
		SC_SIGNAL, SC_FIFO
	};

	private enum Other {
	};

	private String lastLine = "";

	private Scope currentScope;
	
	public List<Signal> getSignals(List<String> lines, List<ModuleSignature> moduleSignatures) {
		
		currentScope = Scope.GLOBAL;

		signals = new ArrayList<Signal>();
		this.moduleSignatures = new ArrayList<ModuleSignature>();

		for (String line : lines) {
			line = StringUtil.trim(line);

			if (currentScope.equals(Scope.METHOD)) { // sc_main
				// Check end of method
				if (StringUtil.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}
				// Check for module instance
				
				// Check for signal instance
				
				// Check for 

				// else ignore
			} else if (currentScope.equals(Scope.MODULE)) {

				// else check for method start
				if (StringUtil.doesExists(line, "{")) {
					// Change scope
					this.changeScope(Boolean.TRUE);
				}
				// else check end of module
				else if (StringUtil.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}
				// TODO: Check for port name
				else {
					/*if (currentModuleSignature != null) {

						for (PortType portName : PortType.values()) {
							if (StringUtil.doesExists(line, portName.toString())) {
								// parse port name
								Port port = new Port();

								port.setName(StringUtil.getSubString(line, " ", ";"));
								port.setDataType(StringUtil.getSubString(line, "<", ">"));
								port.setType(portName.toString().toLowerCase());
								port.setModuleSignature(currentModuleSignature);
								
								currentModuleSignature.getPorts().add(port);
							}
						}
					}*/
				}

				// else ignore
			} else if (currentScope.equals(Scope.MODULE_METHOD)) {
				// Check end of method
				if (StringUtil.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}

				// else ignore
			} else if (currentScope.equals(Scope.GLOBAL)) {
				// Check for module
				// If no module then check for method
				if (StringUtil.doesExists(line, "{")) {
					// Change scope
					this.changeScope(Boolean.TRUE);
				}
				// else ignore
			}
			
			lastLine = line;
		}
		
		return signals;
	}

	public List<ModuleSignature> getModules(List<String> lines) {

		currentScope = Scope.GLOBAL;

		moduleSignatures = new ArrayList<ModuleSignature>();

		for (String line : lines) {
			line = StringUtil.trim(line);

			if (currentScope.equals(Scope.METHOD)) {
				// Check end of method
				if (StringUtil.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}

				// else ignore
			} else if (currentScope.equals(Scope.MODULE)) {

				// else check for method start
				if (StringUtil.doesExists(line, "{")) {
					// Change scope
					this.changeScope(Boolean.TRUE);
				}
				// else check end of module
				else if (StringUtil.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}
				// TODO: Check for port name
				else {
					if (currentModuleSignature != null) {

						for (PortType portName : PortType.values()) {
							if (StringUtil.doesExists(line, portName.toString())) {
								// parse port name
								Port port = new Port();

								port.setName(StringUtil.getSubString(line, " ", ";"));
								port.setDataType(StringUtil.getSubString(line, "<", ">"));
								port.setType(portName.toString().toLowerCase());
								port.setModuleSignature(currentModuleSignature);
								
								currentModuleSignature.getPorts().add(port);
							}
						}
					}
				}

				// else ignore
			} else if (currentScope.equals(Scope.MODULE_METHOD)) {
				// Check end of method
				if (StringUtil.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}

				// else ignore
			} else if (currentScope.equals(Scope.GLOBAL)) {
				// Check for module
				// If no module then check for method
				if (StringUtil.doesExists(line, "{")) {
					// Change scope
					this.changeScope(Boolean.TRUE);
				}
				// else ignore
			}
			
			lastLine = line;
		}

		return moduleSignatures;
	}

	private void changeScope(Boolean increase) {

		if (currentScope == null) {
			return;
		}
		try {
			if (Boolean.FALSE.equals(increase)) {

				if (currentScope.equals(Scope.GLOBAL)) {
					// Already at highest scope
					return;
				} else if (currentScope.equals(Scope.MODULE)) {
					currentScope = Scope.GLOBAL;

					moduleSignatures.add((ModuleSignature) currentModuleSignature.clone());

					currentModuleSignature = null;
				} else if (currentScope.equals(Scope.MODULE_METHOD)) {
					currentScope = Scope.MODULE;
				} else {
					currentScope = Scope.GLOBAL;
				}

			} else {

				if (currentScope.equals(Scope.GLOBAL)) {
					// Check if it should goto module or method
					if (StringUtil.doesExists(lastLine, Keyword.SC_MODULE.toString())) {

						currentScope = Scope.MODULE;

						currentModuleSignature = new ModuleSignature();

						String moduleName = StringUtil.getSubString(lastLine, "(", ")");

						currentModuleSignature.setName(moduleName);

					} else if (StringUtil.doesExists(lastLine, Keyword.SC_MAIN.toString())) {
						currentScope = Scope.METHOD;
					}
				} else if (currentScope.equals(Scope.MODULE)) {
					currentScope = Scope.MODULE_METHOD;
				} else if (currentScope.equals(Scope.MODULE_METHOD)) {
					/* currentScope = Scope.MODULE; */
					// Already at lowest scope
					return;
				} else {
					// Already at lowest scope
					return;
				}

			}
		} catch (CloneNotSupportedException e) {
		}

	}

}
