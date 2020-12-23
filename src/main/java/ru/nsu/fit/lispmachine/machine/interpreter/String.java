package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class String implements Expression {
	private java.lang.String value;

	public String(java.lang.String value) {
		// любой символ не численный или не особый символ
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
		String string = (String) o;
		return Objects.equals(value, string.value);
	}

	@Override public int hashCode() {
		return Objects.hash(value);
	}
}