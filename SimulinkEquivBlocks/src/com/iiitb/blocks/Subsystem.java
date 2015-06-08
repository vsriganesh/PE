package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.iiitb.cfg.Accfg;

import expression.Expression;
import expression.SubBlockExpression;
import expression.Variable;


/** Class for Subsystem block. 
 * This is a special kind of block whose Accfg instance is derived from Accfg instances of enclosed subsystems 
 *
 */
public class Subsystem extends Block{

	
	private List<Expression> inputList = new ArrayList<Expression>();
	
	
	@Override
	public Expression expression() {
		// TODO Auto-generated method stub
		Variable retVar=null;
		try {
			setExpressionSet(true);
			return(new SubBlockExpression(((Variable)getAccfg().getOutput().get(0)).getName(),this,getOutput()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null
		setExpressionSet(true);
		return retVar;
		
		//return getName() + "=" + getAccfg().getOutput();
	}

	@Override

	public ArrayList<Expression> getInput() {
		// TODO Auto-generated method stub
		return ((ArrayList<Expression>)inputList);
	}

	@Override
	
	public void setInput(String input,String port) {
		try {
			inputList.add(new Variable(input,this));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Subsystem(Accfg accfg, String name) {
		super(name);

		/*
		 * For eg : If output of inner subsystem is 'sum' and name of the
		 * current subsystem is 'subsystem' then add 'subsystem = sum' and then
		 * proceed with evaluation of current subsystem
		 */
		
		// Set to access in expression() method
		setAccfg(accfg);
		
		
		
		
		Accfg accfgLocal = new Accfg();
		accfgLocal.setFp(accfg.getFp());
		
		accfgLocal.getFp().add(expression());
		accfgLocal.setDelay(accfg.getDelay());
		accfgLocal.setInit(accfg.getInit());
		accfgLocal.setInput(inputList);

		// Set output as block name
		List<Expression> outputTemp = new ArrayList<Expression>();
		outputTemp.add(getOutput());
		accfgLocal.setOutput(outputTemp);
		
		setAccfg(accfgLocal);
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

	

}
