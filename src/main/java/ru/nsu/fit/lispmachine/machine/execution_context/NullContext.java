package ru.nsu.fit.lispmachine.machine.execution_context;

import java.util.List;

import ru.nsu.fit.lispmachine.machine.interpreter.Expression;

public class NullContext implements ExecutionContext {
	public static NullContext INSTANCE = new NullContext();

	private NullContext() {
	}

	@Override public Expression lookupVariable(String name) {
		return null;
	}

	@Override public ExecutionContext extendContext(List<String> variables, List<Expression> values) {
		return null;
	}

	@Override public void addDefinition(String name, Expression value) {

	}

	@Override public void setValue(String name, Expression value) {

	}
}
