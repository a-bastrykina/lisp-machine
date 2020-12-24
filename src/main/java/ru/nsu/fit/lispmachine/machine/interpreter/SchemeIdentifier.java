package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class SchemeIdentifier implements Expression {
	private final String value;

	public SchemeIdentifier(String value) {
		this.value = Objects.requireNonNull(value);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		return context.lookupVariable(value);
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SchemeIdentifier string = (SchemeIdentifier) o;
		return Objects.equals(value, string.value);
	}

	@Override public int hashCode() {
		return Objects.hash(value);
	}
}
