package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public class QuotedExpr implements Expression {

    private final Expression body;

    QuotedExpr(Expression body) {
        this.body = body;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return body;
    }

    @Override
    public List<Expression> getArguments() {
        return null;
    }
}
