package ru.nsu.fit.lispmachine.machine.interpreter;

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
    public boolean isTrue() {
        return value;
    }

    @Override
    public String toString() {
        return value ? "#T" : "#F";
    }
}
