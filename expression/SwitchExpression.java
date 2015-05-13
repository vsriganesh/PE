package expression;

import program.IProgram;
import visitors.IExprVisitor;

public class SwitchExpression extends Expression implements IBinaryExpression {

	private IExpression mLHS;
	private IExpression mRHS;
	
	private IExpression thresholdExpr;
	
	
	private IExpression output;
	
	public SwitchExpression(IProgram program, IExpression lhs, IExpression rhs,IExpression condInput,IExpression output,String criteria ,String threshold,IExpression thresh,IExpression criter,IExpression thresholdExpr) throws Exception {
		super(program, lhs.getType());
		if(lhs.getType() != rhs.getType()) {
			Exception e = new Exception("SwitchExpression : Type error.");
			throw e;
		}
		this.mLHS = lhs;
		this.mRHS = rhs;
	
		this.output = output;
		this.thresholdExpr = thresholdExpr;
		
		
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
		
	
		return( this.output+"=("+thresholdExpr.toString()+"?"+this.mLHS.toString()+":"+this.mRHS.toString() + ")");
		
		
		
		
	}

	@Override
	public void accept(IExprVisitor<?> visitor) throws Exception {
			visitor.visit(this.mRHS);
			visitor.visit(this.mLHS);
	}
}
