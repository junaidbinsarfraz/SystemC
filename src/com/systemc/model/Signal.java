package com.systemc.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Signal class is use to store signal data like name, type, dataType and connected moduleInstaces
 */
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
	
	/**
	 * The toString() method is use to show object in string/displayable form
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Signal Name: " + this.name + "\n");
		if(moduleInstances != null && moduleInstances.size() > 0) {
			sb.append("\nModule Instances:\n\n");
		} else {
			sb.append("\nNo Module Instance\n");
		}
		
		Iterator entries = moduleInstances.entrySet().iterator();
		while (entries.hasNext()) {
		  Entry thisEntry = (Entry) entries.next();
		  ModuleInstance moduleInstance = (ModuleInstance) thisEntry.getValue();
		  
			sb.append(moduleInstance.toString() + "\n");
		  
		}
		
		return sb.toString();
	}

}
