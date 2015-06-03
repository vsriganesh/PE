package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iiitb.cfg.Accfg;

import expression.AddExpression;
import expression.Expression;
import expression.Variable;

public class Sum extends Block {

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

	static {

		signList.put(1, "++");
		signList.put(2, "+-");
		signList.put(3, "-+");
		
		// -- is not implemented
		signList.put(4, "--");

	}

	@Override
	public void setInput(String input,String port) {
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
			// System.out.println("Input1 is set");

		} else {
			setInput2(input);
			
			try {
				rhs = new Variable(input, this);
				if (lhs != null && rhs != null)
					setInputSetFlag(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("Input2 is set");
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
			if(getSign() == 1){
				setExpressionSet(true);
			return((new AddExpression(this, lhs, rhs,getOutput(),getSign())));
			}
			
			else if(getSign() == 2)
			{
				setExpressionSet(true);	
				return((new AddExpression(this, lhs, rhs,getOutput(),getSign())));
			}
			else
			{
				setExpressionSet(true);
				return((new AddExpression(this, rhs, lhs,getOutput(),getSign())));
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setExpressionSet(true);
		return null;

	}

	/*public Sum(String input1, String input2, String name, int sign) {

		
		
		super(name);

		setInput1(input1);
		setInput2(input2);

		Accfg accfgObj = new Accfg();
		List<Expression> input = new ArrayList<Expression>();
		input.add(getLhs());
		input.add(getRhs());
		accfgObj.setInput(input);
		accfgObj.setOutput(getOutput());
		List<Expression> expr = new ArrayList<Expression>();
		expr.add(expression());
		accfgObj.setFp(expr);
		setAccfg(accfgObj);

	}*/

	public Sum(String blockName) {
		// TODO Auto-generated constructor stub

		super(blockName);
		Accfg accfgObj = new Accfg();
		List<Expression> outputTemp = new ArrayList<Expression>();
		outputTemp.add(getOutput());
		accfgObj.setOutput(outputTemp);
		setAccfg(accfgObj);

	}

	
	//  Methods overridden for IProgram 

/*	@Override
	public Variable addVariable(Variable arg0) {
		// TODO Auto-generated method stub

		super.addVariable(arg0);
		if (getInput2() == null || getInput2() == "")
			lhs = arg0;
		else
			rhs = arg0;

		return arg0;
	}*/
	/*
	@Override
	public Set<Variable> getVariables() {

		Set<Variable> retSet = new HashSet<Variable>();
		if (getLhs() != null)
			retSet.add((Variable)getLhs());
		if (getRhs() != null)
			retSet.add((Variable)getRhs());
		if (getOutput() != null)
			System.out.println(getOutput().getClass());
			retSet.add((Variable)getOutput());
		// TODO Auto-generated method stub
		return retSet;
	}

	@Override
	public boolean hasVariable(Variable arg0) {
		// TODO Auto-generated method stub
		Set<Variable> checkSet = getVariables();
		Iterator iter = checkSet.iterator();
		while (iter.hasNext()) {
			if (arg0.equals((Variable)iter.next())) {
				return true;

			}
		}

		return false;
	}*/

}
