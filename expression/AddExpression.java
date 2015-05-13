package expression;

import program.IProgram;
import visitors.IExprVisitor;

public class AddExpression extends Expression implements IBinaryExpression, IArithmeticExpression {

	private IExpression mLHS;
	private IExpression mRHS;
	private IExpression output;
	private int sign;
	
	public AddExpression(IProgram program, IExpression lhs, IExpression rhs,IExpression output,int sign) throws Exception {
		super(program, lhs.getType());
		if(lhs.getType() != rhs.getType()) {
			Exception e = new Exception("AddExpression : Type error.");
			throw e;
		}
		this.mLHS = lhs;
		this.mRHS = rhs;
		this.output = output;
		this.sign = sign;
	}

	@Override
	public IExpression getLHS() {
		return this.mLHS;
	}

	@Override
	public IExpression getRHS() {
		return this.mRHS;
	}

	@Override
	public String toString() {
		if(this.sign==1)
		return this.output+"="+"(" + this.mLHS.toString() + " + " + this.mRHS.toString() + ")";
		else
			return this.output+"="+"(" + this.mLHS.toString() + " - " + this.mRHS.toString() + ")";
	}

	@Override
	public void accept(IExprVisitor<?> visitor) {
		try {
			visitor.visit(this.mRHS);
			visitor.visit(this.mLHS);
		} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
