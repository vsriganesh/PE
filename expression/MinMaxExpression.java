package expression;

import java.util.List;

import program.IProgram;
import visitors.IExprVisitor;

public class MinMaxExpression extends Expression implements IBinaryExpression, IArithmeticExpression {

	private List<Expression> input;
	
	private IExpression output;
	private int sign;
	
	public MinMaxExpression(IProgram program, List<Expression> input,IExpression output,int sign) throws Exception {
		super(program, input.get(0).getType());
		for(Expression temp:input)
		{
			if(temp.getType() != Type.INT)
			{
				Exception e = new Exception("AddExpression : Type error.");
				throw e;
			}
			
		}
		
		
		this.input = input;
		this.output = output;
		this.sign = sign;
	}



	@Override
	public String toString() {
		if(this.sign==1)
		return this.output+"="+"Min(" + input + ")";
		else
			return this.output+"="+"Max(" + input + ")";
	}

	
	 // Not implemented
	@Override
	public void accept(IExprVisitor<?> visitor) {
	
	}


   // Not implemented since here number of inputs is not static
	@Override
	public IExpression getLHS() {
		// TODO Auto-generated method stub
		return null;
	}


	// Not implemented since here number of inputs is not static
	@Override
	public IExpression getRHS() {
		// TODO Auto-generated method stub
		return null;
	}
}
