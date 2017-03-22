package com.systemc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ModuleInstance implements Serializable {

	private String name;
	private Map<Port, String> portInstances = new HashMap<Port, String>();
	private ModuleSignature moduleSignature = new ModuleSignature();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Port, String> getPortInstances() {
		return portInstances;
	}

	public void setPortInstances(Map<Port, String> portInstances) {
		this.portInstances = portInstances;
	}

	public ModuleSignature getModuleSignature() {
		return moduleSignature;
	}

	public void setModuleSignature(ModuleSignature moduleSignature) {
		this.moduleSignature = moduleSignature;
	}

}
