package com.systemc.controller;

import java.util.ArrayList;
import java.util.List;

import com.systemc.model.ModuleInstance;
import com.systemc.model.ModuleSignature;
import com.systemc.model.Port;
import com.systemc.model.Signal;
import com.systemc.util.StringUtil;

/**
 * The class MainController is use to getSignals and getModules. This is where
 * the main logic of program written
 */
public class MainController {

	private List<ModuleSignature> moduleSignatures;
	private ModuleSignature currentModuleSignature;
	private List<Signal> signals;
	private List<ModuleInstance> moduleInstances;

	private enum Scope {
		MODULE, METHOD, MODULE_METHOD, GLOBAL
	};

	private enum Keyword {
		SC_MODULE, SC_MAIN, MAIN
	};

	// Accepted port types
	private enum PortType {
		SC_IN, SC_OUT, SC_INOUT, SC_PORT, SC_FIFO_IN, SC_FIFO_OUT, SC_CLK_IN
	};

	// Accepted signal types
	private enum SignalType {
		SC_SIGNAL, SC_FIFO
	};

	private enum Other {
	};

	private String lastLine = "";

	private Scope currentScope;

	/**
	 * The method getSignals() is use to extract signal and signal connections
	 * from the lines given to it
	 * 
	 * @param lines
	 *            read from input file
	 * @param moduleSignatures
	 *            list of module signature
	 * @return list of signals with connections
	 */
	public List<Signal> getSignals(final List<String> lines, final List<ModuleSignature> moduleSignatures) {

		// Initially current scope is global
		currentScope = Scope.GLOBAL;

		moduleInstances = new ArrayList<ModuleInstance>();
		signals = new ArrayList<Signal>();
		this.moduleSignatures = new ArrayList<ModuleSignature>();

		// Parse each line
		for (String line : lines) {
			line = StringUtil.trim(line);
			
			// Check if current scope is main method (sc_main)
			if (currentScope.equals(Scope.METHOD)) { // sc_main
				// Check end of method
				if (StringUtil.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}

				// Detect signal
				for (SignalType signalType : SignalType.values()) {
					if (StringUtil.doesExists(line, signalType.toString())) {
						// Extract data type of signal

						if (StringUtil.doesExists(line, "<") && StringUtil.doesExists(line, ">")) {
							// This is a signal
							String signalDataType = StringUtil.getSubString(line, "<", ">");

							Signal signal = new Signal();

							signal.setDataType(signalDataType);

							if (StringUtil.doesExists(line, " ") && StringUtil.doesExists(line, ";")) {

								signal.setName(StringUtil.getSubString(line, " ", "("));

								// Add to signal list
								this.signals.add(signal);
							}
						}
					}
				}

				// Detect Module Instance
				for (ModuleSignature moduleSignature : moduleSignatures) {

					if (StringUtil.doesExists(line, moduleSignature.getName())) {
						if (StringUtil.doesExists(line, "*") && StringUtil.doesExists(line, " ") && StringUtil.doesExists(line, "=")) {
							// Get module name of module instance

							String moduleName = StringUtil.getSubString(line, " ", "=");

							moduleName = moduleName.replaceAll("\\s", "");

							ModuleInstance moduleInstance = new ModuleInstance();

							moduleInstance.setModuleSignature(moduleSignature);
							moduleInstance.setName(moduleName);

							// Add to module instances
							moduleInstances.add(moduleInstance);
							
						} else if (StringUtil.doesExists(line, " ") && StringUtil.doesExists(line, "(") && StringUtil.doesExists(line, ")")
								&& StringUtil.doesExists(line, "\"")) {

							// Get module name of module instance

							String moduleName = StringUtil.getSubString(line, " ", "(");

							moduleName = moduleName.replaceAll("\\s", "");

							ModuleInstance moduleInstance = new ModuleInstance();

							moduleInstance.setModuleSignature(moduleSignature);
							moduleInstance.setName(moduleName);

							// Add to module instances
							moduleInstances.add(moduleInstance);

						}
					}
				}

				// Detect signal link to module instance's port
				for (Signal signal : signals) {
					if (StringUtil.doesExists(line, signal.getName())) {

						for (ModuleInstance moduleInstance : moduleInstances) {
							if (StringUtil.doesExists(line, moduleInstance.getName())) {

								for (Port port : moduleInstance.getModuleSignature().getPorts()) {
									if (StringUtil.doesExists(line, port.getName())) {

										// Verify and Link module instance's port via signal instance

										String portName = "", moduleName = "";
										
										// First syntax c.a(f1);
										if (StringUtil.doesExists(line, ">")) {

											portName = StringUtil.getSubString(line, ">", "(");

											portName = portName.replaceAll("\\s", "");

											moduleName = line.split("->") != null ? line.split("->")[0] : "";

											moduleName = moduleName.replaceAll("\\s", "");

										} 
										// Second syntax c->a(f1);
										else if (StringUtil.doesExists(line, ".")) {

											portName = StringUtil.getSubString(line, ".", "(");

											portName = portName.replaceAll("\\s", "");

											moduleName = line.split("\\.") != null ? line.split("\\.")[0] : "";

											moduleName = moduleName.replaceAll("\\s", "");

										}

										String signalName = StringUtil.getSubString(line, "(", ")");

										signalName = signalName.replaceAll("\\s", "");

										if (port.getName().equals(portName) && moduleInstance.getName().equals(moduleName)
												&& signal.getName().equals(signalName)) {

											moduleInstance.getPortInstances().add(port);

											signal.getModuleInstances().put(moduleInstance.getName(), moduleInstance);
										}
									}
								}

							}
						}
					}
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

	/**
	 * The method getModules() is use to extract module signatures from the
	 * given lines
	 * 
	 * @param lines
	 *            read from input file
	 * @return list of module signatures
	 */
	public List<ModuleSignature> getModules(List<String> lines) {

		// Initially current scope is global
		currentScope = Scope.GLOBAL;

		moduleSignatures = new ArrayList<ModuleSignature>();

		// Parse each line
		for (String line : lines) {
			line = StringUtil.trim(line);

			// Check if current scope is main method (sc_main)
			if (currentScope.equals(Scope.METHOD)) {
				// Check end of method
				if (StringUtil.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}

				// else ignore
			} 
			// Check if current scope is module
			else if (currentScope.equals(Scope.MODULE)) {

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
				// Check for port name
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

								// Add port to current module
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

	/**
	 * The method changeScope() is use to current change of the program
	 * 
	 * @param increase
	 *            local to global means increase else decrease
	 */
	private void changeScope(Boolean increase) {

		// Check if current scope is null 
		if (currentScope == null) {
			return;
		}
		try {
			// { determines that it's scope is increased
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

			} 
			// } determines that it's scope is decreased
			else {

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
