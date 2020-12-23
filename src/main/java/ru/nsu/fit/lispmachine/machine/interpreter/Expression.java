package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;
import java.util.stream.Stream;

public interface Expression {
    Expression evaluate(ExecutionContext context);

    default boolean isTrue() {
        return true;
    }

    default Expression apply(List<Expression> args, ExecutionContext context) {
        throw new IllegalArgumentException(this + "Not callable");
    }
}
