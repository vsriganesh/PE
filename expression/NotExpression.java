package expression;

import program.IProgram;
import visitors.IExprVisitor;

public class NotExpression extends Expression {

	private IExpression mExpression;
	private IExpression output;

	public NotExpression(IProgram program, IExpression exp,IExpression output) throws Exception {
		super(program, Type.BOOLEAN);
		if(exp.getType() != Type.BOOLEAN) {
			Exception e = new Exception("NotExpression : Type error in " + exp.toString());
			throw e;
		}
		this.mExpression = exp;
		this.output = output;
	}

	public IExpression getExpression() {
		return this.mExpression;
	}
	
	@Override
	public void accept(IExprVisitor<?> visitor) throws Exception {
		visitor.visit(this.mExpression);
	}
	
	@Override
	public String toString() {
		if(this.output!=null)
		return this.output+"=(" + " not " + this.mExpression.toString() + ")";
		else
			return "(" + " not " + this.mExpression.toString() + ")";
	}

}
