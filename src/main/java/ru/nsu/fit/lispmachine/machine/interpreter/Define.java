package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.List;

public class Define implements Expression {
    private final String name;
    private Expression value;

    public Define(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        var evaluated = value.evaluate(context);
        context.addDefinition(name, value);
        return evaluated;
    }

    @Override
    public List<Expression> getArguments() {
        return new ArrayList<>();
    }
}
