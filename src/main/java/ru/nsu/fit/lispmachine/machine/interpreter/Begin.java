package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.List;
import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class Begin implements Expression {

	private final List<Expression> operands;

	public Begin(List<Expression> operands) {
		Objects.requireNonNull(operands);
		if (operands.isEmpty()) {
			throw new IllegalArgumentException("Expecting non-empty list of operands");
		}
		this.operands = operands;
	}

	@Override public Expression evaluate(ExecutionContext context) {
		Expression result = null;
		for (var expr : operands) {
			result = expr.evaluate(context);
		}
		return result;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Begin begin = (Begin) o;
		return Objects.equals(operands, begin.operands);
	}

	@Override public int hashCode() {
		return Objects.hash(operands);
	}
}
