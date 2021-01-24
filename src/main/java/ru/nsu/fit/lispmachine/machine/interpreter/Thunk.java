package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

/**
 * A wrapper to represent an expression
 * with deferred evaluation mode.
 */
public class Thunk implements Expression {
    private final ExecutionContext thunkContext;
    private Expression expression;

    /**
     * @param expression
     * @param context
     */
    Thunk(Expression expression, ExecutionContext context) {
        this.expression = expression;
        this.thunkContext = context;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return expression.evaluate(thunkContext);
    }
}

