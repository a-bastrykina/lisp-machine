package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class String implements Expression {


    private String value;

    public String(String value) {
        // любой символ не численный или не особый символ
        this.value = value;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return this;
    }
}
