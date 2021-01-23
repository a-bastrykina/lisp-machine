package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class SchemeChar implements Expression {
	Character value;

	/**
	 * @param value
	 * @throws NullPointerException if an argument is null
	 */
	public SchemeChar(Character value) {
		this.value = Objects.requireNonNull(value);
	}

	@Override public Expression evaluate(ExecutionContext context) {
		return this;
	}

	/**
	 * @return character value
	 */
	public Character getValue() {
		return value;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SchemeChar that = (SchemeChar) o;
		return Objects.equals(value, that.value);
	}

	@Override public int hashCode() {
		return Objects.hash(value);
	}
}
