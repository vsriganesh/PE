package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.List;

import com.iiitb.cfg.Accfg;

import expression.AndExpression;
import expression.Expression;
import expression.NotExpression;
import expression.OrExpression;
import expression.Variable;
/** Implementation for AND ,OR,NOT,XNOR */
public class LogicalOperator extends Block {

	

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
				
				lhs = new Variable(input,"boolean",this);
				
				if ((lhs != null && rhs != null) || getSign()==3)
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
				rhs = new Variable(input,"boolean", this);
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
		if(getLhs()!=null)
		inputs.add(getLhs());
		if(getRhs()!=null)
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
			
			System.out.println("******************************************* "+getSign());
			
			if(getSign()==1)
			{
				setExpressionSet(true);
				return( new AndExpression(this, lhs, rhs,getOutput()));
			}
			
			if(getSign()==2)
			{
				setExpressionSet(true);
			return( new OrExpression(this, lhs, rhs,getOutput()));
			}
			
			if(getSign()==3)
			{
				setExpressionSet(true);
			  return(new NotExpression(this,lhs,getOutput()));
			}
			
			if(getSign()==4)
			{
			Expression tempAnd = new AndExpression(this, lhs, rhs,null);
			Expression lhsNot = new NotExpression(this, lhs,null);
			Expression rhsNot = new NotExpression(this, rhs,null);
			Expression tempLhsRhsAnd = new AndExpression(this, lhsNot, rhsNot,null);
			
			setExpressionSet(true);
			return new OrExpression(this, tempAnd, tempLhsRhsAnd,getOutput());
			
			
			
			}
			
			if(getSign()==5)
			{
			Expression tempOr = new OrExpression(this, lhs, rhs,getOutput());
			setExpressionSet(true);
			return(new NotExpression(this, tempOr,getOutput()));
			}
			
			if(getSign()==6)
			{
			
				
				Expression tempEqual = new AndExpression(this, lhs, rhs,getOutput());
				setExpressionSet(true);
			return(new NotExpression(this,tempEqual,getOutput()));
			}
			
			if(getSign()==7)
			{
			
				
			
				Expression lhsNot = new NotExpression(this, lhs,null);
				Expression tempAnd = new AndExpression(this, lhsNot, rhs,null);
				Expression rhsNot = new NotExpression(this, rhs,null);
				Expression tempLhsRhsAnd = new AndExpression(this, lhs, rhsNot,null);
				
				setExpressionSet(true);
				return new OrExpression(this, tempAnd, tempLhsRhsAnd,getOutput());
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setExpressionSet(true);
		return null;

	}

	public LogicalOperator(String blockName) {
		// TODO Auto-generated constructor stub

		super(blockName);
		Accfg accfgObj = new Accfg();
		List<Expression> outputTemp = new ArrayList<Expression>();
		outputTemp.add(getOutput());
		accfgObj.setOutput(outputTemp);
		setAccfg(accfgObj);
		// By default 'AND'
		setSign(1);

	}

}
