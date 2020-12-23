package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class SchemeNumber implements Expression {


    private Number value;

    public SchemeNumber(Number value) {
        this.value = value;
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
}
