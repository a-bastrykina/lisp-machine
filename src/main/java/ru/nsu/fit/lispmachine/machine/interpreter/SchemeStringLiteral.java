package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class SchemeStringLiteral implements Expression {
	private String value;

	public SchemeStringLiteral(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SchemeStringLiteral that = (SchemeStringLiteral) o;
		return Objects.equals(value, that.value);
	}

	@Override public int hashCode() {
		return Objects.hash(value);
	}

	@Override public Expression evaluate(ExecutionContext context) {
		return this;
	}
}
