package expression;

import program.IProgram;
import visitors.IExprVisitor;

public class SubBlockExpression  extends Expression {

	private final String mValue;
	private IExpression output;
	public SubBlockExpression(String value, IProgram program,IExpression output) throws Exception {
		super(program, Type.INT);
		this.mValue = value;
		this.output =output;
	}

	public String getValue() {
		return this.mValue;
	}
	
	@Override
	public String toString() {
	
		return this.output+"="+this.mValue;
	}

	@Override
	public void accept(IExprVisitor<?> visitor) {
	}
}
