package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public interface Expression {
    Expression evaluate(ExecutionContext context);

    default Expression apply(List<Expression> args, ExecutionContext context) {
        throw new IllegalArgumentException(this + "Not callable");
    }

    default Object castTo(String clazzName) {
        if (clazzName.equals("java.lang.String"))
            return toString();
        if (clazzName.equals("java.lang.Boolean"))
            return true;
        throw new IllegalArgumentException("Cannot cast this to " + clazzName);
    }
}
