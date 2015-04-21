package com.iiitb.inter;

import java.util.ArrayList;

import expression.Expression;
/** Interface for block classes
 *
 **/
public interface IBlock {
	
	public Expression expression();
	public ArrayList<Expression> getInput();
	public void setInput(String input,String port);

	

}
