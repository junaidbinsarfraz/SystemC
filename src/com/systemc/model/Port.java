package com.systemc.model;

import java.io.Serializable;

/**
 * The class Port is use to store name, type, datatype and moduleSignature where it belongs 
 */
public class Port implements Serializable, Cloneable {

	private String name;
	private String type; // sc_in, sc_out, sc_inout, sc_port, sc_fifo_in, sc_fifo_out, sc_clk_in
	private String dataType; // int, bool etc
	private ModuleSignature moduleSignature = new ModuleSignature();

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

	public ModuleSignature getModuleSignature() {
		return moduleSignature;
	}

	public void setModuleSignature(ModuleSignature moduleSignature) {
		this.moduleSignature = moduleSignature;
	}

	/**
	 * The clone() method is use to clone the object
	 */
	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
	/**
	 * The toString() method is use to show object in string/displayable form
	 */
	@Override
	public String toString() {
		return "\tName: " + this.name + "\n\ttype: " + this.type + "\n\tdata-type: " + this.dataType;
	}
	
	/**
	 * The equals() method is use to compare two objects and check if both are same or not
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
	        return false;
	    }
	    if (!Port.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final Port other = (Port) obj;
	    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
	        return false;
	    }
	    if(this.moduleSignature == null || other.moduleSignature == null || (this.moduleSignature.getName() == null) ? (other.moduleSignature.getName() != null) : !this.moduleSignature.getName().equals(other.moduleSignature.getName())) {
	    	return false;
	    }
	    return true;
	}

}
