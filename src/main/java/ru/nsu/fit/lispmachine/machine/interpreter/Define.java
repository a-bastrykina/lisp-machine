package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Define implements Expression {
	private final SchemeIdentifier name;
	private final List<Expression> parameters;
	private final Expression body;

	public Define(SchemeIdentifier name, List<Expression> parameters, Expression body) {
		this.name = name;
		this.parameters = parameters;
		this.body = body;
		if (parameters.stream().anyMatch(e -> !(e instanceof SchemeIdentifier))) {
			throw new IllegalArgumentException("Define arguments must be strings!");
		}
	}

	public Define(SchemeIdentifier name, Expression body) {
		this(name, Collections.emptyList(), body);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		var lambda = new Lambda(parameters, body);
		var evaluated = lambda.evaluate(context);
		context.addDefinition(name.toString(), evaluated);
		return evaluated;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Define define = (Define) o;
		return Objects.equals(name, define.name) &&
				Objects.equals(parameters, define.parameters) &&
				Objects.equals(body, define.body);
	}

	@Override public int hashCode() {
		return Objects.hash(name, parameters, body);
	}

}
