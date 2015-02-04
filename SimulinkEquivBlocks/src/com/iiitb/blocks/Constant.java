package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.List;

import com.iiitb.cfg.Accfg;

/**
 * Constant block equivalent to Simulink block - Constant
 * 
 */
public class Constant extends Block {

	public Constant(String name) {

		super(name);
		Accfg accfgObj = new Accfg();
		accfgObj.setOutput(name);
		setAccfg(accfgObj);
	}

	private String value;
	// private String name;
	private Accfg accfg;

	public Accfg getAccfg() {
		return accfg;
	}

	public void setAccfg(Accfg accfg) {
		this.accfg = accfg;
	}

	/*
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 */

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String expression() {

		return getName() + "=" + getValue();
	}

	public Constant(String value, String name) {

		super(name);
		setValue(value);

		Accfg accfgObj = new Accfg();

		accfgObj.setOutput(getName());
		List<String> expr = new ArrayList<String>();
		expr.add(expression());
		accfgObj.setFp(expr);
		setAccfg(accfgObj);

	}

	@Override
	public ArrayList<String> getInput() {
		// TODO Auto-generated method stub
		
		//NO input for constant block. Returning null intentionally
		return null;
	}

	@Override
	public void setInput(String input) {
		// TODO Auto-generated method stub
		
		// Intentionally left blank
		// NO input for constant block
		
	}

}
