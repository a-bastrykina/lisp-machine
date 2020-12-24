package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class SchemeChar implements Expression {
	Character value;

	public SchemeChar(Character value) {
		this.value = value;
	}

	@Override public Expression evaluate(ExecutionContext context) {
		return this;
	}

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
