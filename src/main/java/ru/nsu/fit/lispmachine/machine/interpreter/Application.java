package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Application implements Expression {
    protected List<Expression> arguments;
    protected ExecutionContext context;
    private final Expression operator;

    public Application(Expression operator, List<Expression> arguments, ExecutionContext context) {
        this.operator = operator;
        this.arguments = arguments;
        this.context = context;
    }

    @Override
    public Expression evaluate(ExecutionContext c) {
        return apply(operator.evaluate(c), arguments, c);
    }

    @Override
    public List<Expression> getArguments() {
        return new ArrayList<>();
    }

    private Expression apply(Expression body, List<Expression> args, ExecutionContext c) {
        var evaluatedArgs = args.stream().map(e -> e.evaluate(c)).collect(Collectors.toList());
        return body.evaluate(evaluatedArgs, context);
    }
}
