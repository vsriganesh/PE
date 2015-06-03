package expression;

import program.IProgram;
import visitors.IExprVisitor;

public class AndExpression extends Expression implements IBinaryExpression {

	private IExpression mLHS;
	private IExpression mRHS;
	private IExpression output;
	public AndExpression(IProgram program, IExpression lhs, IExpression rhs,IExpression output) throws Exception {
		super(program, Type.BOOLEAN);
		if(lhs == null) {
			Exception e = new Exception("AndExpression : LHS is null.");
			throw e;
		}
		if(rhs == null) {
			Exception e = new Exception("AndExpression : RHS is null.");
			throw e;
		}
		if((lhs.getType() != Type.BOOLEAN) || (rhs.getType() != Type.BOOLEAN)) {
			Exception e = new Exception("AndExpression : Type error.");
			throw e;
		}
		this.mLHS = lhs;
		this.mRHS = rhs;
		this.output = output;
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
		return this.output+"=(" + this.mLHS.toString() + " AND " + this.mRHS.toString() + ")";
		else
			return "(" + this.mLHS.toString() + " AND " + this.mRHS.toString() + ")";
					
	}

	@Override
	public void accept(IExprVisitor<?> visitor) throws Exception {
			visitor.visit(this.mRHS);
			visitor.visit(this.mLHS);
	}
}
