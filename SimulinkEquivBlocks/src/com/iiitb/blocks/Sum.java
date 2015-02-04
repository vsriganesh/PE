package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iiitb.cfg.Accfg;

public class Sum extends Block {

	public static final Map<Integer, String> signList = new HashMap<Integer, String>();
	private Accfg accfg;

	public Accfg getAccfg() {
		return accfg;
	}

	public void setAccfg(Accfg accfg) {
		this.accfg = accfg;
	}

	static {

		signList.put(1, "++");
		signList.put(2, "+-");
		signList.put(3, "--");

	}

	@Override
	public void setInput(String input) {
		if(this.input1 == null || this.input1==""){
			setInput1(input);
			//System.out.println("Input1 is set");
		
		}
		else{
			setInput2(input);
			setInputSetFlag(true);
			//System.out.println("Input2 is set");
			}
	}

	@Override
	public ArrayList<String> getInput()
	{
		List<String> inputs = new ArrayList<String>();
		inputs.add(getInput1());
		inputs.add(getInput2());
		return (ArrayList<String>) inputs;
	}
	
	private String input1;
	private String input2;
	
	

	private int sign ;
	// private String name;

	/*
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 */

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public String getInput1() {
		return input1;
	}

	public void setInput1(String input1) {
		this.input1 = input1;
	}

	public String getInput2() {
		return input2;
	}

	public void setInput2(String input2) {
		this.input2 = input2;
	}

	@Override
	public String expression() {

		return (getName()+"="+input1 + "+" + input2);
	}

	
	
	
	public Sum(String input1, String input2, String name, int sign) {

		super(name);

		setInput1(input1);
		setInput2(input2);

		Accfg accfgObj = new Accfg();
		List<String> input = new ArrayList<String>();
		input.add(input1);
		input.add(input2);
		accfgObj.setInput(input);
		accfgObj.setOutput(getName());
		List<String> expr = new ArrayList<String>();
		expr.add(expression());
		accfgObj.setFp(expr);
		setAccfg(accfgObj);

	}

	public Sum(String blockName) {
		// TODO Auto-generated constructor stub
		
		super(blockName);
		Accfg accfgObj = new Accfg();
		accfgObj.setOutput(blockName);
		setAccfg(accfgObj);
		
	}

}
