package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.List;

public class Application implements Expression {
    private final List<Expression> arguments;
    private final Expression operator;

    public Application(Expression operator, List<Expression> arguments) {
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        //todo correct apply
        return apply(operator.evaluate(context), arguments);
    }

    @Override
    public List<Expression> getArguments() {
        return new ArrayList<>();
    }

    private Expression apply(Expression body, List<Expression> args) {
        return null;
    }
}
