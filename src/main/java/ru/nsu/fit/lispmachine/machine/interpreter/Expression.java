package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

/**
 * An interface to represent a single element
 * of Scheme Java Object model
 */
public interface Expression {
    /**
     * Transform an expression depending on execution context
     * @param context execution context
     * @return new expression
     */
    Expression evaluate(ExecutionContext context);

    /**
     * Perform application computation
     * @param args application arguments
     * @param context execution context
     * @return new expression
     * @throws IllegalArgumentException if the expression is not callable
     */
    default Expression apply(List<Expression> args, ExecutionContext context) {
        throw new IllegalArgumentException(this + "Not callable");
    }

    /**
     * Cast the expression to a Java object
     * @param clazzName name of desired Java class
     * @return an object of the desired class
     */
    default Object castTo(String clazzName) {
        if (clazzName.equals("java.lang.String"))
            return toString();
        if (clazzName.equals("java.lang.Boolean"))
            return true;
        throw new IllegalArgumentException("Cannot cast this to " + clazzName);
    }
}
