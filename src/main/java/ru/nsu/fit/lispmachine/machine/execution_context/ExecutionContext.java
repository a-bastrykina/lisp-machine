package ru.nsu.fit.lispmachine.machine.execution_context;

import ru.nsu.fit.lispmachine.machine.interpreter.Expression;

import java.util.List;

/**
 * An interface to represent an execution context under
 * which a particular expression is executed
 */
public interface ExecutionContext {

    /**
     * Get value of variable from the context
     * @param name
     * @return
     */
    Expression lookupVariable(String name);

    /*
     * Get a context based on this and
     * extended with new definitions
     * @param variables
     * @param values
     * @return execution context
     */
    ExecutionContext extendContext(List<String> variables, List<Expression> values);

    /**
     * Add a definition to the context
     * @param name
     * @param value
     */
    void addDefinition(String name, Expression value);

    /**
     * Forcing calculations in when interpreter mode is lazy.
     * If not lazy, returns its arg
     * @param expression
     * @return
     */
    Expression getActualExpressionValue(Expression expression);

    /**
     * @return
     */
    boolean isLazyModelSupported();
}
