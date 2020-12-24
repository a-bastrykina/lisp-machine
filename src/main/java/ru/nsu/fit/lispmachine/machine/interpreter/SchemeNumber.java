package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.Objects;

public class SchemeNumber implements Expression {
	private Number value;

	public SchemeNumber(Number value) {
		this.value = Objects.requireNonNull(value);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		return this;
	}

	public Number getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SchemeNumber number = (SchemeNumber) o;
		return Objects.equals(value, number.value);
	}

    @Override
    public  Object castTo(String className) {
        //todo more cases
        if (className.equals("java.lang.Double") || className.equals("double")) {
            return value.doubleValue();
        }
        else {
            if (className.equals("java.lang.Int") || className.equals("int")) {
                return value.intValue();
            }
        }
        return null;
    }

    @Override public int hashCode() {
        return Objects.hash(value);
    }
}
