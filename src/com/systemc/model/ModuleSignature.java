package com.systemc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModuleSignature implements Serializable, Cloneable {
	
	private String name;
	private List<Port> ports = new ArrayList<Port>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}
	
	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Module Name: " + this.name + "\n");
		if(ports != null && ports.size() > 0) {
			sb.append("\nPorts:\n\n");
		} else {
			sb.append("\nNo Port\n");
		}
		
		for(Port port : ports) {
			sb.append(port.toString() + "\n\n");
		}
		
		return sb.toString();
	}
	
}
