package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SchemeList implements Expression {
	private final List<Expression> values;

	public SchemeList(List<Expression> values) {
	    this.values = Objects.requireNonNull(values);
	}

	public List<Expression> getValues() {
		return values;
	}

	@Override
	public Object castTo(String clazzName) {
        if (clazzName.equals("java.lang.String"))
            return toString();
        if (clazzName.equals("java.lang.Boolean"))
            return !values.isEmpty();
        throw new IllegalArgumentException("Cannot cast this to " + clazzName);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		return this;
	}

	@Override
	public String toString() {
		return "(" + values.stream().map(Expression::toString).collect(Collectors.joining(" ")) + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SchemeList string = (SchemeList) o;
		return Objects.equals(values, string.values);
	}

	@Override
	public int hashCode() {
		return Objects.hash(values);
	}
}

