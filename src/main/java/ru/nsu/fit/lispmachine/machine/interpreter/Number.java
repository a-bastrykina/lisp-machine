package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;
import java.util.Objects;

public class Number implements Expression {
	private java.lang.Number value;

	public Number(java.lang.Number value) {
		// Double or Integer
		this.value = value;
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		return this;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Number number = (Number) o;
		return Objects.equals(value, number.value);
	}

	@Override public int hashCode() {
		return Objects.hash(value);
	}
}
