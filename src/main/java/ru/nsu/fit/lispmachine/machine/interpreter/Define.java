package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;
import java.util.stream.Collectors;

public class Define implements Expression {
    private final SchemerString name;
    private final List<Expression> parameters;
    private final Expression body;

    public Define(SchemerString name, List<Expression> parameters, Expression body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
        if (parameters.stream().anyMatch(e -> ! (e instanceof SchemerString))) {
            throw new IllegalArgumentException("Define arguments must be strings!");
        }
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        var lambda = new Lambda(parameters, body);
        var evaluated = lambda.evaluate(context);
        context.addDefinition(name.toString(), evaluated);
        return evaluated;
    }

}
