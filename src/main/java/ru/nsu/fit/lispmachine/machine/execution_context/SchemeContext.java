package ru.nsu.fit.lispmachine.machine.execution_context;

import ru.nsu.fit.lispmachine.machine.interpreter.Thunk;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.calculus.*;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists.*;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.Equal;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.Less;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.More;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.Not;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to hold Scheme execution context.
 * By default, supports the following symbols:
 * + - * / %
 * &lt; &gt; = not
 * list cons car cdr
 * null?
 */
public class SchemeContext implements ExecutionContext {
    private final Map<String, Expression> bindings = new HashMap<>();
    private final boolean lazyModelSupported;

    /**
     * Create a context that does not support lazy calculations
     */
    public SchemeContext() {
        this(false);
    }

    /**
     * @param lazy are the lazy calculations supported
     */
    public SchemeContext(boolean lazy) {
        lazyModelSupported = lazy;
        bindings.put("+", new Plus());
        bindings.put("-", new Minus());
        bindings.put("*", new Multiply());
        bindings.put("%", new Mod());
        bindings.put("/", new Division());

        bindings.put(">", new More());
        bindings.put("<", new Less());
        bindings.put("=", new Equal());
        bindings.put("not", new Not());

        bindings.put("list", new SchemeListCall());
        bindings.put("cons", new ConsCall());
        bindings.put("car", new CarCall());
        bindings.put("cdr", new CdrCall());
        bindings.put("null?", new CheckEmptyListCall());
    }

    @Override
    public Expression lookupVariable(String name) {
        if (!bindings.containsKey(name)) {
            throw new IllegalArgumentException("Definition with name " + name + " not found");
        }
        return bindings.get(name);
    }


    @Override
    public ExecutionContext extendContext(List<String> variables, List<Expression> values) {
        if (variables.size() != values.size()) {
            throw new IllegalArgumentException("Variables count doesn't match values count");
        }

        var res = new SchemeContext(lazyModelSupported);
        res.bindings.putAll(bindings);
        for (int i = 0; i < values.size(); i++) {
            res.addDefinition(variables.get(i), values.get(i));
        }
        return res;
    }

    @Override
    public void addDefinition(String name, Expression value) {
        bindings.put(name, value);
    }

    @Override
    public Expression getActualExpressionValue(Expression expression) {
        return (!lazyModelSupported) ? expression : forceExpression(expression.evaluate(this));
    }

    private Expression forceExpression(Expression expression) {
        if (expression instanceof Thunk)
            return getActualExpressionValue(expression);
        return expression;
    }

    @Override
    public String toString() {
        return bindings.toString();
    }

    @Override
    public boolean isLazyModelSupported() {
        return lazyModelSupported;
    }
}
