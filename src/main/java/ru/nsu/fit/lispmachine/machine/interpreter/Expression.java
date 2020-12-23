package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public interface Expression {
    Expression evaluate(ExecutionContext context);

    default boolean isTrue() {
        return true;
    }

    List<Expression> getArguments();

    default Object getNumericValue() {
        throw new IllegalArgumentException(this + "Is not numeric value");
    }

}
