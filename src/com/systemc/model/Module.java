package com.systemc.model;

import java.util.List;

public class Module {
	
	private String name;
	private List<Port> ports;

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
	
}
