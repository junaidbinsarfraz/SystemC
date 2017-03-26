package com.systemc.model;

import java.util.HashMap;
import java.util.Map;

public class Signal {

	private String name;
	private String type; // sc_signal or sc_fifo
	private String dataType; // int, bool etc
	private Map<String, ModuleInstance> moduleInstances = new HashMap<String, ModuleInstance>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Map<String, ModuleInstance> getModuleInstances() {
		return moduleInstances;
	}

	public void setModuleInstances(Map<String, ModuleInstance> moduleInstances) {
		this.moduleInstances = moduleInstances;
	}

}
