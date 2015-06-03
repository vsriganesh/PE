package expression;

import program.IProgram;
import visitors.IExprVisitor;

public class OrExpression extends Expression implements IBinaryExpression {

	private IExpression mLHS;
	private IExpression mRHS;
	private IExpression output;

	public OrExpression(IProgram program, IExpression lhs, IExpression rhs,IExpression output) throws Exception {
		super(program, Type.BOOLEAN);
		if((lhs.getType() != Type.BOOLEAN) || (rhs.getType() != Type.BOOLEAN)) {
			Exception e = new Exception("OrExpression : Type error.");
			throw e;
		}
		this.mLHS = lhs;
		this.mRHS = rhs;
		this.output=output;
	}

	@Override
	public void accept(IExprVisitor<?> visitor) throws Exception {
		visitor.visit(this.mRHS);
		visitor.visit(this.mLHS);
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
		if(this.output!=null)
		return this.output+"=(" + this.mLHS.toString() + " OR " + this.mRHS.toString() + ")";
		else
			return "(" + this.mLHS.toString() + " OR " + this.mRHS.toString() + ")";
	}
}
