package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.iiitb.cfg.Accfg;
import com.iiitb.utility.ParseXML;


import expression.EqualsExpression;
import expression.Expression;
import expression.Variable;

/**
 * Constant block equivalent to Simulink block - Constant
 * 
 */
public class InPort extends Block {

	public InPort(String name) {

		super(name);
		Accfg accfgObj = new Accfg();
		List<Expression> outputTemp = new ArrayList<Expression>();
		outputTemp.add(getOutput());
		accfgObj.setOutput(outputTemp);
		setAccfg(accfgObj);
		//Default port is set
		setPort("1");
		setValue(port);
		
		ParseXML.portMap.remove(getPort());
		
		/* Here Fp is set directly with value considered as 1. If the value is not 1 this 
		 * FP will be overwritten in the ConstHelper.java 
		 */
		List<Expression> expr = new ArrayList<Expression>();
		expr.add(this.expression());
		this.getAccfg().setFp(expr);
	}

	private String port;
	private Expression valueFromSubsystem;
	private String value;
	private Accfg accfg;

	public Accfg getAccfg() {
		return accfg;
	}

	public void setAccfg(Accfg accfg) {
		this.accfg = accfg;
	}

	/*
	 * public String getName() { return name;
	 * 
	 * }
	 * 
	 * public void setName(String name) { this.name = name; }
	 */

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		setPort(value);
		this.value = ParseXML.portMap.get(value);
		try {
			this.valueFromSubsystem = new Variable(ParseXML.portMap.get(value),this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Expression expression() {

		Variable retVar = null;
		try {
			setExpressionSet(true);
			return new EqualsExpression(this,getOutput(),this.valueFromSubsystem);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return null
		setExpressionSet(true);
		return retVar;

		

	}

	public InPort(String port, String name) {

		super(name);
		
		setPort(port);
		setValue(port);
		ParseXML.portMap.remove(getPort());
		
		Accfg accfgObj = new Accfg();

		List<Expression> outputTemp = new ArrayList<Expression>();
		outputTemp.add(getOutput());
		accfgObj.setOutput(outputTemp);
		List<Expression> expr = new ArrayList<Expression>();
		expr.add(expression());
		accfgObj.setFp(expr);
		setAccfg(accfgObj);

	}

	@Override
	public ArrayList<Expression> getInput() {
		// TODO Auto-generated method stub

		// NO input for constant block. Returning null intentionally
		return null;
	}

	@Override
	public void setInput(String input,String port) {
		// TODO Auto-generated method stub

		// Intentionally left blank
		// NO input for constant block

	}

	// IProgram methods to override - Intentionally left blank

	@Override
	public Variable addVariable(Variable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Variable> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasVariable(Variable arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
