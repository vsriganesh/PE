package com.iiitb.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iiitb.cfg.Accfg;

import expression.Expression;
import expression.MinMaxExpression;
import expression.Variable;

public class MinMax extends Block {

	public static final Map<Integer, String> signList = new HashMap<Integer, String>();

	private List<Expression> inputList = new ArrayList<Expression>();
	private int noOfInputs;


	public int getNoOfInputs() {
		return noOfInputs;
	}

	public void setNoOfInputs(int noOfInputs) {
		this.noOfInputs = noOfInputs;
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

			try {
				inputList.add(new Variable(input,this));
				System.out.println("inputList size ****************************** "+inputList.size());
				System.out.println("getNoOfInputs() ****************** "+getNoOfInputs());
				if(inputList.size() == getNoOfInputs() )
				{
					System.out.println("inside set flag minmax block");
					setInputSetFlag(true);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	public ArrayList<Expression> getInput() {
		
		return (ArrayList<Expression>) inputList;
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
			return ((new MinMaxExpression(this, inputList, getOutput(),
					getSign())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setExpressionSet(true);
		return null;

	}

	public MinMax(String blockName) {
		// TODO Auto-generated constructor stub

		super(blockName);
		Accfg accfgObj = new Accfg();
		List<Expression> outputTemp = new ArrayList<Expression>();
		outputTemp.add(getOutput());
		accfgObj.setOutput(outputTemp);
		setAccfg(accfgObj);
		setSign(1);
		setNoOfInputs(1);

	}

	public List<Expression> getInputList() {
		return inputList;
	}

	public void setInputList(List<Expression> inputList) {
		this.inputList = inputList;
	}

}

