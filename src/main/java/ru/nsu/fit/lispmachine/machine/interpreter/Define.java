package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Define implements Expression {
	private final SchemeIdentifier name;
	private final List<Expression> parameters;
	private final List<Expression> body;

	public Define(SchemeIdentifier name, List<Expression> parameters, List<Expression> body) {
		this.name = Objects.requireNonNull(name);
		this.parameters = Objects.requireNonNull(parameters);
		this.body = Objects.requireNonNull(body);
		if (parameters.stream().anyMatch(e -> !(e instanceof SchemeIdentifier))) {
			throw new IllegalArgumentException("Define arguments must be strings!");
		}
	}

	public Define(SchemeIdentifier name, Expression body) {
		this(name, Collections.emptyList(), List.of(body));
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
	    Function<Expression, Expression> action = e -> {
	        var evaluated = e.evaluate(context);
	        context.addDefinition(name.toString(), evaluated);
	        return evaluated;
        };
        if (parameters.isEmpty()) {
            return action.apply(body.get(0));
        }
        var begin = new Begin(body);
        return action.apply(new Lambda(parameters, begin));
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
