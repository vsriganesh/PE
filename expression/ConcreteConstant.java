package expression;

import program.IProgram;
import visitors.IExprVisitor;

public class ConcreteConstant  extends Expression implements IArithmeticExpression {

	private final int mValue;
	private IExpression output;
	public ConcreteConstant(int value, IProgram program,IExpression output) throws Exception {
		super(program, Type.INT);
		this.mValue = value;
		this.output= output;
	}

	public int getValue() {
		return this.mValue;
	}
	
	@Override
	public String toString() {
		Integer i = new Integer(this.mValue);
		return this.output+"="+i.toString();
	}

	@Override
	public void accept(IExprVisitor<?> visitor) {
	}
}
