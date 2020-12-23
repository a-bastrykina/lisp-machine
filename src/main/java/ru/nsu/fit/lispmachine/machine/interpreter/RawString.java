package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class RawString implements Expression {


    private String value;

    public RawString(String value) {
        this.value = value;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return context.lookupVariable(value);
    }
}
