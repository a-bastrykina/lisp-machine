package ru.nsu.fit.lispmachine.machine.interpreter.native_calls;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;

/**
 * An abstract class to represent Java Platform call.
 */
public abstract class NativeCall implements Expression {

	@Override
	public Expression evaluate(ExecutionContext context) {
		return this;
	}
}
