package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TryExcept implements Expression {

	private final Expression body;
	private final Map<SchemeString, Expression> exceptionsAndCatches;

	public TryExcept(Expression body, Map<SchemeString, Expression> exceptionsAndCatches) {
		this.body = Objects.requireNonNull(body);
		this.exceptionsAndCatches = Objects.requireNonNull(exceptionsAndCatches);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		try {
			return body.evaluate(context);
		} catch (Exception exc) {
			var excName = exc.getClass().getName();
			var catchBody = exceptionsAndCatches.get(new SchemeString(excName));
			if (catchBody != null) {
				return catchBody.evaluate(context);
			}
			throw new IllegalArgumentException("Could find matching catch clause for " + exc.getClass().getName(), exc);
		}
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TryExcept tryExcept = (TryExcept) o;
		return body.equals(tryExcept.body) &&
				exceptionsAndCatches.equals(tryExcept.exceptionsAndCatches);
	}

	@Override public int hashCode() {
		return Objects.hash(body, exceptionsAndCatches);
	}
}
