package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public class Atom <T> implements Expression {

    private final T value;

    public Atom(T value) {
        this.value = value;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return this;
    }

    @Override
    public List<Expression> getArguments() {
        return null;
    }

    @Override
    public Object getNumericValue() {
        if ( value instanceof Character) {
            throw new IllegalArgumentException(this + "Is not numeric value");
        }
        return value;
    }
}
