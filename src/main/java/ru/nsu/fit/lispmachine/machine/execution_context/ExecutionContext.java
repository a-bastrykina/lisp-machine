package ru.nsu.fit.lispmachine.machine.execution_context;

import ru.nsu.fit.lispmachine.machine.interpreter.Expression;

import java.util.List;

public interface ExecutionContext {
    Expression lookup(String name);

    ExecutionContext extendContext(List<String> variables, List<Expression> values);

    void addDefinition(String name, Expression value);

    void setValue(String name, Expression value);

}
