package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iiitb.cfg.Accfg;

import expression.DivideExpression;
import expression.Expression;
import expression.Variable;

public class Divide extends Block {

	public static final Map<Integer, String> signList = new HashMap<Integer, String>();

	private Expression lhs;
	private Expression rhs;

	public Expression getLhs() {
		return lhs;
	}

	public void setLhs(Variable lhs) {
		this.lhs = lhs;
	}

	public Expression getRhs() {
		return rhs;
	}

	public void setRhs(Variable rhs) {
		this.rhs = rhs;
	}

	private Accfg accfg;

	public Accfg getAccfg() {
		return accfg;
	}

	public void setAccfg(Accfg accfg) {
		this.accfg = accfg;
	}

	@Override
	public void setInput(String input, String port) {

		if (port.equalsIgnoreCase("1")) {
			setInput1(input);

			try {
				lhs = new Variable(input, this);
				if (lhs != null && rhs != null)
					setInputSetFlag(true);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("Divide Input1 is set ******************");

		}

		else {
			setInput2(input);

			try {
				rhs = new Variable(input, this);
				if (lhs != null && rhs != null)
					setInputSetFlag(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("Divide Input2 is set *************************");
		}
	}

	@Override
	public ArrayList<Expression> getInput() {
		List<Expression> inputs = new ArrayList<Expression>();
		inputs.add(getLhs());
		inputs.add(getRhs());
		return (ArrayList<Expression>) inputs;
	}

	private String input1;
	private String input2;

	private int sign;

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
	public Expression expression() {

		try {
			setExpressionSet(true);
			return ((new DivideExpression(this, lhs, rhs, getOutput(),
					getSign())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setExpressionSet(true);
		return null;

	}

	public Divide(String blockName) {
		// TODO Auto-generated constructor stub

		super(blockName);
		Accfg accfgObj = new Accfg();
		List<Expression> outputTemp = new ArrayList<Expression>();
		outputTemp.add(getOutput());
		accfgObj.setOutput(outputTemp);
		setAccfg(accfgObj);

	}

}
