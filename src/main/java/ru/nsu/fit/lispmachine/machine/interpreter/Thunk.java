package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class Thunk implements Expression {
    private final ExecutionContext thunkContext;
    private Expression expression;

    Thunk(Expression expression, ExecutionContext context) {
        this.expression = expression;
        this.thunkContext = context;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return expression.evaluate(thunkContext);
    }
}

