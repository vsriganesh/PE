package expression;

import program.IProgram;
import visitors.IExprVisitor;

public class Variable extends Expression {
	private final String mName;

	public Variable(String name, IProgram program) throws Exception {
		super(program, Type.INT);
		this.mName = name;
		if(program.hasVariable(this)) {
			Exception e = new Exception("Variable named '" + name + "' already exists in program.");
			throw e;
		}
		program.addVariable(this);
	}

	public Variable(String name, String type, IProgram program) throws Exception {
		super(program, type);
		this.mName = name;
		if(program.hasVariable(this)) {
			Exception e = new Exception("Variable named '" + name + "' already exists in program.");
			throw e;
		}
		program.addVariable(this);
	}

	public String getName() {
		return this.mName;
	}

	@Override
	public String toString() {
		return this.mName;
	}

	@Override
	public void accept(IExprVisitor<?> visitor) {
	}
	
	@Override
	public boolean equals(Object o)
	{
		
		if(o instanceof Variable)
		{
		if(this.mName.equals(((Variable)o).mName))
			return true;
		}
		
		return false;
		
	}


	
}
