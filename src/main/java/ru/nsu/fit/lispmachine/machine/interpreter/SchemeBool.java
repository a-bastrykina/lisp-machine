package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class SchemeBool implements Expression {
	private final boolean value;

	public SchemeBool(boolean value) {
		this.value = value;
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		return this;
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public Object castTo(String clazzName) {
        if (clazzName.equals("java.lang.String"))
            return toString();
        if (clazzName.equals("java.lang.Boolean"))
            return value;
        throw new IllegalArgumentException("Cannot cast this to " + clazzName);
	}

	@Override
	public String toString() {
		return value ? "#T" : "#F";
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SchemeBool that = (SchemeBool) o;
		return value == that.value;
	}

	@Override public int hashCode() {
		return Objects.hash(value);
	}
}
