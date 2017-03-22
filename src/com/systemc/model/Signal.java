package com.systemc.model;

import java.util.ArrayList;
import java.util.List;

public class Signal {

	private String name;
	private String type; // sc_signal or sc_fifo
	private String dataType; // int, bool etc
	private List<ModuleInstance> moduleInstances = new ArrayList<ModuleInstance>();

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

	public List<ModuleInstance> getModuleInstances() {
		return moduleInstances;
	}

	public void setModuleInstances(List<ModuleInstance> moduleInstances) {
		this.moduleInstances = moduleInstances;
	}

}
