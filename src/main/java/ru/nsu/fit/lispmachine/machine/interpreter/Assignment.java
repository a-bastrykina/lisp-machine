package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.Objects;

/**
 * Class to represent an assignment expression of Scheme.
 */
public class Assignment implements Expression {

	private final SchemeIdentifier name;
	private final Expression value;

	/**
	 * @param name variable name
	 * @param value variable value
	 * @throws NullPointerException if the arguments are null
	 */
	public Assignment(SchemeIdentifier name, Expression value) {
		this.name = Objects.requireNonNull(name);
		this.value = Objects.requireNonNull(value);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
	    var ex = context.lookupVariable(name.toString());
	    var evaluated = this.value.evaluate(context);
	    context.addDefinition(name.getValue(), evaluated);
	    return evaluated;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Assignment that = (Assignment) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(value, that.value);
	}

	@Override public int hashCode() {
		return Objects.hash(name, value);
	}
}
