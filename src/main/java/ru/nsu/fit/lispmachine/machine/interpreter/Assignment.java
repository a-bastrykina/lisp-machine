package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public class Assignment implements Expression {

    private final String name;
    private final Expression value;

    Assignment(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        var evaluated = this.value.evaluate(context);
        context.addDefinition(name, evaluated); //todo lazy evaluatuin
        return evaluated;
    }

    @Override
    public List<Expression> getArguments() {
        return null;
    }
}
