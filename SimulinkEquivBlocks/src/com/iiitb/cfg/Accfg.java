package com.iiitb.cfg;

import java.util.ArrayList;
import java.util.List;

import expression.Expression;

public class Accfg {

	private List<Expression> init = new ArrayList<Expression>();
	private List<Expression> input = new ArrayList<Expression>();
	private Expression output;

	private List<Expression> fp = new ArrayList<Expression>();

	public List<Expression> getInit() {
		return init;
	}

	public void setInit(List<Expression> init) {
		this.init = init;
	}

	public List<Expression> getInput() {
		return input;
	}

	public void setInput(List<Expression> input) {
		this.input = input;
	}

	public Expression getOutput() {
		return output;
	}

	public void setOutput(Expression output) {
		this.output = output;
	}

	public List<Expression> getFp() {
		return fp;
	}

	public void setFp(List<Expression> fp) {
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
		 toRetTemp.append("FP : " + getFp().toString());
		 return toRetTemp.toString();
		 
		
	}
	
	
	

}
