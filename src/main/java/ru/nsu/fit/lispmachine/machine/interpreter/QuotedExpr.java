package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class QuotedExpr implements Expression {

	private final Expression body;

	public QuotedExpr(Expression body) {
		this.body = Objects.requireNonNull(body);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		return body;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		QuotedExpr that = (QuotedExpr) o;
		return Objects.equals(body, that.body);
	}
	@Override public int hashCode() {
		return Objects.hash(body);
	}
}
