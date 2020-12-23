package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class IfClause implements Expression {

    private final Expression pred;
    private final Expression trueBody;
    private final Expression falseBody;

    public IfClause(Expression pred, Expression trueBody, Expression falseBody) {
        this.pred = pred;
        this.trueBody = trueBody;
        this.falseBody = falseBody;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return pred.evaluate(context).isTrue() ? trueBody.evaluate(context) : falseBody.evaluate(context);
    }
}
