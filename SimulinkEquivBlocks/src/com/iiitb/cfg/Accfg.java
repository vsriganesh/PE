package com.iiitb.cfg;

import java.util.ArrayList;
import java.util.List;

public class Accfg {

	private List<String> init = new ArrayList<String>();
	private List<String> input = new ArrayList<String>();
	private String output;

	private List<String> fp = new ArrayList<String>();

	public List<String> getInit() {
		return init;
	}

	public void setInit(List<String> init) {
		this.init = init;
	}

	public List<String> getInput() {
		return input;
	}

	public void setInput(List<String> input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public List<String> getFp() {
		return fp;
	}

	public void setFp(List<String> fp) {
		this.fp = fp;
	}
	
	@Override
	public String toString()
	{
		 StringBuffer toRetTemp= new StringBuffer("ACCFG :");
		 toRetTemp.append("\n");
		 toRetTemp.append("Init : " + getInit());
		 toRetTemp.append("\n");
		 toRetTemp.append("Input : " + getInput());
		 toRetTemp.append("\n");
		 toRetTemp.append("Output : " + getOutput());
		 toRetTemp.append("\n");
		 toRetTemp.append("FP : " + getFp());
		 return toRetTemp.toString();
		 
		
	}
	
	
	

}
