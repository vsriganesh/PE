package com.iiitb.utility;

public class DestNode {
	
	String name;
	String port;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	@Override
	public String toString()
	{
		return this.getName();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this.getName().equalsIgnoreCase(((DestNode)o).getName()))
				return true;
		else
			return false;
	}
	
}
