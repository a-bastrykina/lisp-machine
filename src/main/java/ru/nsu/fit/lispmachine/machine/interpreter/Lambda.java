package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;
import java.util.Objects;

public class Lambda implements Expression {
	private final List<Expression> parameters;
	private final Expression body;

	public Lambda(List<Expression> parameters, Expression body) {
		this.parameters = Objects.requireNonNull(parameters);
		this.body = Objects.requireNonNull(body);
	}

    @Override
    public Expression evaluate(ExecutionContext context) {
        if (parameters.isEmpty())
            return body;
        return new Application(body, parameters);
    }

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Lambda lambda = (Lambda) o;
		return Objects.equals(parameters, lambda.parameters) &&
				Objects.equals(body, lambda.body);
	}

	@Override public int hashCode() {
		return Objects.hash(parameters, body);
	}
}
