package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iiitb.cfg.Accfg;

import expression.EqualsExpression;
import expression.Expression;
import expression.GreaterThanExpression;
import expression.OrExpression;
import expression.SwitchExpression;
import expression.Variable;

public class Switch extends Block {

	public static final Map<Integer, String> signList = new HashMap<Integer, String>();

	
	// Inputs for switch
	private Expression lhs = null;
	private Expression rhs = null;
	private Expression condInput = null;
	
	// Attributes captured for switch block
	private String criteria;
	private String threshold;
	
	

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
	public void setInput(String input,String port) {
		if (port.equalsIgnoreCase("1") ) {
			setInput1(input);
			try {
				lhs = new Variable(input, this);
				if(rhs!=null && condInput!=null)
				{
						setInputSetFlag(true);
						
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		} else if(port.equalsIgnoreCase("3")){
			setInput2(input);
			
			try {
				rhs = new Variable(input, this);
				if(lhs!=null && condInput!=null)
				{
					setInputSetFlag(true);
				
				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		else
		{
			setInput3(input);
			try {
				condInput = new Variable(input, this);
				if(lhs!=null && rhs!=null)
				{
					setInputSetFlag(true);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public ArrayList<Expression> getInput() {
		List<Expression> inputs = new ArrayList<Expression>();
		inputs.add(getLhs());
		inputs.add(getRhs());
		inputs.add(getCondInput());
		return (ArrayList<Expression>) inputs;
	}

	private String input1;
	private String input2;
	private String input3;

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
			Expression thresh = new Variable(getThreshold(),this);
			Expression criter = new Variable(getCriteria(),this);
			Expression thresholdExpr = null;
			//this.criteria = "GreaterAndEqual";
			if(this.criteria.equalsIgnoreCase("GreaterAndEqual"))
			{
				Expression tempGreater = new GreaterThanExpression(this, condInput, thresh,null);
				Expression tempEqual = new EqualsExpression(this, condInput, thresh);
				thresholdExpr = new OrExpression(this, tempGreater, tempEqual,null);
				
			}
				else
			thresholdExpr = new GreaterThanExpression(this, condInput, thresh,null);
			
			setExpressionSet(true);
			return( (new SwitchExpression(this, lhs, rhs,condInput,getOutput(),getCriteria(),getThreshold(),thresh,criter,thresholdExpr)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setExpressionSet(true);
		return null;

	}

	public Switch(String input1, String input2, String input3,String name, int sign) {

		
		
		super(name);

		setInput1(input1);
		setInput2(input2);
		setInput3(input3);
		

		Accfg accfgObj = new Accfg();
		List<Expression> input = new ArrayList<Expression>();
		input.add(getLhs());
		input.add(getRhs());
		accfgObj.setInput(input);
		List<Expression> outputTemp = new ArrayList<Expression>();
		outputTemp.add(getOutput());
		accfgObj.setOutput(outputTemp);
		List<Expression> expr = new ArrayList<Expression>();
		expr.add(expression());
		accfgObj.setFp(expr);
		setAccfg(accfgObj);

	}

	public Switch(String blockName) {
		// TODO Auto-generated constructor stub

		super(blockName);
		Accfg accfgObj = new Accfg();
		List<Expression> outputTemp = new ArrayList<Expression>();
		outputTemp.add(getOutput());
		accfgObj.setOutput(outputTemp);
		setAccfg(accfgObj);
		//Default threshold is set
		setThreshold("0");
		//Default condition/criteria is set
		setCriteria("GreaterAndEqual");

	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public Expression getCondInput() {
		return condInput;
	}

	public void setCondInput(Expression condInput) {
		this.condInput = condInput;
	}

	public String getInput3() {
		return input3;
	}

	public void setInput3(String input3) {
		this.input3 = input3;
	}

	


}
