package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public interface Expression {
    Expression evaluate(ExecutionContext context);

    default boolean isTrue() {
        return true;
    }

    default Expression apply(List<Expression> args, ExecutionContext context) {
        throw new IllegalArgumentException(this + "Not callable");
    }

    //todo maybe isTrueRedundant
    default Object castTo(String clazzName) {
        if (clazzName.equals("java.lang.String"))
            return toString();
        throw new IllegalArgumentException("Cannot cast this to " + clazzName);
    }
}
