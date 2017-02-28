package com.systemc.controller;

import java.util.ArrayList;
import java.util.List;

import com.systemc.model.Module;
import com.systemc.model.Port;
import com.systemc.util.StringUtil;

public class MainController {

	private List<Module> modules;
	private Module currentModule;

	private enum Scope {
		MODULE, METHOD, MODULE_METHOD, GLOBAL
	};

	private enum Keyword {
		SC_MODULE, SC_MAIN, MAIN
	};

	private enum PortName {
		SC_IN, SC_OUT, SC_INOUT, SC_PORT, SC_FIFO_IN, SC_FIFO_OUT, SC_CLK_IN
	};

	private enum Other {
	};

	private String lastLine = "";

	private Scope currentScope;

	public List<Module> getModules(List<String> lines) {

		currentScope = Scope.GLOBAL;

		modules = new ArrayList<Module>();

		for (String line : lines) {
			line = StringUtil.trim(line);

			if (currentScope.equals(Scope.METHOD)) {
				// Check end of method
				if (this.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}

				// else ignore
			} else if (currentScope.equals(Scope.MODULE)) {

				// else check for method start
				if (this.doesExists(line, "{")) {
					// Change scope
					this.changeScope(Boolean.TRUE);
				}
				// else check end of module
				else if (this.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}
				// TODO: Check for port name
				else {
					if (currentModule != null) {

						for (PortName portName : PortName.values()) {
							if (this.doesExists(line, portName.toString())) {
								// parse port name
								Port port = new Port();

								port.setName(this.getSubString(line, " ", ";"));
								port.setDataType(this.getSubString(line, "<", ">"));
								port.setType(portName.toString().toLowerCase());
								
								currentModule.getPorts().add(port);
							}
						}
					}
				}

				// else ignore
			} else if (currentScope.equals(Scope.MODULE_METHOD)) {
				// Check end of method
				if (this.doesExists(line, "}")) {
					// Change scope
					this.changeScope(Boolean.FALSE);
				}

				// else ignore
			} else if (currentScope.equals(Scope.GLOBAL)) {
				// Check for module
				// If no module then check for method
				if (this.doesExists(line, "{")) {
					// Change scope
					this.changeScope(Boolean.TRUE);
				}
				// else ignore
			}
			
			lastLine = line;
		}

		return modules;
	}

	private Boolean doesExists(String parent, String child) {
		return parent.toLowerCase().contains(child.toLowerCase());
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

					modules.add((Module) currentModule.clone());

					currentModule = null;
				} else if (currentScope.equals(Scope.MODULE_METHOD)) {
					currentScope = Scope.MODULE;
				} else {
					currentScope = Scope.GLOBAL;
				}

			} else {

				if (currentScope.equals(Scope.GLOBAL)) {
					// Check if it should goto module or method
					if (this.doesExists(lastLine, Keyword.SC_MODULE.toString())) {

						currentScope = Scope.MODULE;

						currentModule = new Module();

						String moduleName = this.getSubString(lastLine, "(", ")");

						currentModule.setName(moduleName);

					} else if (this.doesExists(lastLine, Keyword.SC_MAIN.toString())) {

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

	private String getSubString(String parent, String delimator1, String delimator2) {
		return parent.substring(parent.indexOf(delimator1) + 1, parent.indexOf(delimator2));
	}

}
