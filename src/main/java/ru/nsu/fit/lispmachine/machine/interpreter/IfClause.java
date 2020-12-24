package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class IfClause implements Expression {

	private final Expression pred;
	private final Expression trueBody;
	private final Expression falseBody;

	public IfClause(Expression pred, Expression trueBody, Expression falseBody) {
		this.pred = Objects.requireNonNull(pred);
		this.trueBody = Objects.requireNonNull(trueBody);
		this.falseBody = Objects.requireNonNull(falseBody);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		return pred.evaluate(context).isTrue() ? trueBody.evaluate(context) : falseBody.evaluate(context);
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		IfClause ifClause = (IfClause) o;
		return Objects.equals(pred, ifClause.pred) &&
				Objects.equals(trueBody, ifClause.trueBody) &&
				Objects.equals(falseBody, ifClause.falseBody);
	}

	@Override public int hashCode() {
		return Objects.hash(pred, trueBody, falseBody);
	}
}
