package ru.nsu.fit.lispmachine.machine.execution_context;

import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.calculus.Division;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.calculus.Minus;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.calculus.Multiply;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.calculus.Plus;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists.CarCall;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists.CdrCall;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists.ConsCall;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists.SchemeListCall;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.Equal;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.Less;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.More;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.Not;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemeContext implements ExecutionContext {
    private final Map<String, Expression> bindings = new HashMap<>();

    public SchemeContext() {
        bindings.put("+", new Plus());
        bindings.put("-", new Minus());
        bindings.put("*", new Multiply());
        bindings.put("/", new Division());

        bindings.put(">", new More());
        bindings.put("<", new Less());
        bindings.put("=", new Equal());
        bindings.put("not", new Not());
        bindings.put("list", new SchemeListCall());
        bindings.put("cons", new ConsCall());
        bindings.put("car", new CarCall());
        bindings.put("cdr", new CdrCall());
    }

    @Override
    public Expression lookupVariable(String name) {
        if (!bindings.containsKey(name)) {
            throw new IllegalArgumentException("Definition with name " + name + "not found");
        }
        return bindings.get(name);
    }


    @Override
    public ExecutionContext extendContext(List<String> variables, List<Expression> values) {
        if (variables.size() != values.size()) {
            throw new IllegalArgumentException("Variables count doesn't match values count");
        }

        var res = new SchemeContext();
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
    public void setValue(String name, Expression value) {
        if (!bindings.containsKey(name)) {
            throw new IllegalArgumentException("Unknown binding " + name);
        }
        bindings.put(name, value);
    }

    @Override
    public String toString() {
        return bindings.toString();
    }
}
