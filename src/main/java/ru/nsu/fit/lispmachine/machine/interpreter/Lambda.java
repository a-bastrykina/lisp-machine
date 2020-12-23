package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public class Lambda implements Expression {
    private final List<Expression> parameters;
    private final Expression body;

    Lambda(List<Expression> parameters, Expression body) {
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        //todo create new context
//        context.addExpression(UUID.randomUUID(), n);
        return new Function(parameters, body, context);
    }

    @Override
    public List<Expression> getArguments() {
        return null;
    }
}
