package com.systemc.model;

public class Port {

	public enum type {
		sc_in, sc_out, sc_inout, sc_port, sc_fifo_in, sc_fifo_out, sc_clk_in
	};

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
