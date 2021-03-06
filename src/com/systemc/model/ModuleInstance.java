package com.systemc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The ModuleInstance class is the instance of ModuleSignature. 
 * It is use to store data of ModuleInstance like name, port instances and ModuleSignature
 */
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
	
	/**
	 * The toString() method is use to show object in string/displayable form
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n\tModuleInstance Name: "+ this.name);
		sb.append("\n\t\tDataType: "+ this.moduleSignature.getName());
		sb.append("\n\t\tConnected port to the signal: ");
		
		for(Port port : portInstances) {
			sb.append("\n\t\t\tPort Name: " + port.getName());
		}
		
		sb.append("\n");
		
		return sb.toString();
	}

}
