package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class SchemerString implements Expression {


    private final String value;

    public SchemerString(String value) {
        this.value = value;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return context.lookupVariable(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
