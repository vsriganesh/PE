package com.iiitb.blocks;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import program.IProgram;

import com.iiitb.cfg.Accfg;
import com.iiitb.inter.IBlock;

import expression.Expression;
import expression.Variable;

public abstract class Block implements IBlock,IProgram {

	//private String name;
	private Expression output;
	private String value;
	private int sign;
	private Accfg accfg;

	boolean inputSetFlag = false;
	
	boolean expressionSet = false;
	
	
	// Methods overridden for IProgram  - STARTS
	
	public boolean isExpressionSet() {
		return expressionSet;
	}

	public void setExpressionSet(boolean expressionSet) {
		this.expressionSet = expressionSet;
	}

	Set<Variable> variableSet = new HashSet<Variable>();
	
	@Override
	public Variable addVariable(Variable arg0) {
		// TODO Auto-generated method stub
			
		variableSet.add(arg0);

		return arg0;
	}
	
	@Override
	public Set<Variable> getVariables() {

		
		return variableSet;
	}

	@Override
	public boolean hasVariable(Variable arg0) {
		// TODO Auto-generated method stub
		Set<Variable> checkSet = getVariables();
		Iterator<Variable> iter = checkSet.iterator();
		while (iter.hasNext()) {
			if (arg0.equals(iter.next())) {
				return true;

			}
		}

		return false;
	}

	// Methods overridden for IProgram - ENDS

	public Expression getOutput() {
		return output;
	}

	public void setOutput(Expression output) {
		this.output = output;
	}

	public boolean isInputSetFlag() {
		return inputSetFlag;
	}

	public void setInputSetFlag(boolean inputSetFlag) {
		this.inputSetFlag = inputSetFlag;
	}

	public Accfg getAccfg() {
		return accfg;
	}

	public void setAccfg(Accfg accfg) {
		this.accfg = accfg;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Block(String name) {
		// TODO Auto-generated constructor stub
		Expression outputExpr = null;
		try {
			outputExpr = new Variable(name,this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.output = outputExpr;
	}

	public Block() {
		// TODO Auto-generated constructor stub
	}
/*
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}*/

	@Override
	public String toString() {

		return (((Variable)getOutput()).getName());

	}

	@Override
	public boolean equals(Object block) {

		if (block instanceof Block) {

			if (this.toString().equalsIgnoreCase(((Block) block).toString())) {

				return true;
			}

		}
		return false;
	}

}
