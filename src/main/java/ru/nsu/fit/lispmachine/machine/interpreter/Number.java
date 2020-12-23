package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public class Number implements Expression {


    private Object value;

    public Number(Object value) {
        // Double or Integer
        this.value = value;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return this;
    }
}
