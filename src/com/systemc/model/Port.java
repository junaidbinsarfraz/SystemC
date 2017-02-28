package com.systemc.model;

import java.io.Serializable;

public class Port implements Serializable, Cloneable {

	/*public enum Type {
		sc_in, sc_out, sc_inout, sc_port, sc_fifo_in, sc_fifo_out, sc_clk_in
	};*/

	private String name;
	private String type;
	private String dataType;

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

	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
	@Override
	public String toString() {
		return "\tName: " + this.name + "\n\ttype: " + this.type + "\n\tdata-type: " + this.dataType;
	}

}
