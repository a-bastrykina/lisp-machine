package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public class Function implements Expression {
    private final List<Expression> args;
    private final Expression body;
    private final ExecutionContext context;

    Function(List<Expression> args, Expression body, ExecutionContext context) {
        this.args = args;
        this.body = body;
        this.context = context;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return null;
    }

    @Override
    public List<Expression> getArguments() {
        return null;
    }
}
