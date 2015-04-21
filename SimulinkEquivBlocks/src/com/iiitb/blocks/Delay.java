package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.iiitb.cfg.Accfg;

import expression.AddExpression;
import expression.ConcreteConstant;
import expression.EqualsExpression;
import expression.Expression;
import expression.Variable;

public class Delay extends Block {


	
	private int initialValue;
	private Expression inp;


	public Delay(String blockName) {
		// TODO Auto-generated constructor stub
		super(blockName);
		Accfg accfgObj = new Accfg();
		accfgObj.setOutput(getOutput());
		
		List<Expression> init = new ArrayList<Expression>();
		accfgObj.setInit(init);
		setAccfg(accfgObj);
	}





	@Override
	public Expression expression() {
		// TODO Auto-generated method stub
		
		try {
			
			List<Expression> initExpression = new ArrayList<Expression>();
			initExpression.add(new ConcreteConstant(getInitialValue(), this, getOutput()));
			getAccfg().setInit(initExpression);
			return (new EqualsExpression(this,getOutput(),getInp()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return null
		
		return null;
	}

	@Override
	public ArrayList<Expression> getInput() {
		// TODO Auto-generated method stub
		List<Expression> inputs = new ArrayList<Expression>();
		inputs.add(getInp());
		
		return (ArrayList<Expression>) inputs;
	}

	@Override
	public void setInput(String input,String port) {
		
		try {
			setInp(new Variable(input, this));
			setInputSetFlag(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*// To be implemented
	@Override
	public Variable addVariable(Variable arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	// To be implemented
	@Override
	public Set<Variable> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}
	// To be implemented
	@Override
	public boolean hasVariable(Variable arg0) {
		// TODO Auto-generated method stub
		return false;
	}*/

	public Expression getInp() {
		return inp;
	}

	public void setInp(Expression inp) {
		this.inp = inp;
	}

	public int getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
	}

}
