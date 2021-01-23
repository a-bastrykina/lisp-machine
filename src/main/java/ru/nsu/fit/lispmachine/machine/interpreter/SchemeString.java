package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class SchemeString implements Expression {
	private String value;

	/**
	 * @param value
	 * @throws NullPointerException if an argument is null
	 */
	public SchemeString(String value) {
		this.value = Objects.requireNonNull(value);
	}

	public String getValue() {
		return value;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SchemeString that = (SchemeString) o;
		return Objects.equals(value, that.value);
	}

	@Override public int hashCode() {
		return Objects.hash(value);
	}

	@Override public Expression evaluate(ExecutionContext context) {
		return this;
	}

    @Override
    public String toString() { return value; }
}
