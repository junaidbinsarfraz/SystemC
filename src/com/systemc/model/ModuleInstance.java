package com.systemc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModuleInstance implements Serializable {

	private String name;
	private List<Port> portInstances = new ArrayList<Port>();
	private ModuleSignature moduleSignature = new ModuleSignature();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ModuleSignature getModuleSignature() {
		return moduleSignature;
	}

	public void setModuleSignature(ModuleSignature moduleSignature) {
		this.moduleSignature = moduleSignature;
	}

	public List<Port> getPortInstances() {
		return portInstances;
	}

	public void setPortInstances(List<Port> portInstances) {
		this.portInstances = portInstances;
	}

}
