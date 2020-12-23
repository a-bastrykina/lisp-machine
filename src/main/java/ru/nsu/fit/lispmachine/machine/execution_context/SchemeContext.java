package ru.nsu.fit.lispmachine.machine.execution_context;

import ru.nsu.fit.lispmachine.machine.interpreter.Atom;
import ru.nsu.fit.lispmachine.machine.interpreter.AtomTypes;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class SchemeContext implements ExecutionContext {
    private final Map<String, Expression> bindings = new HashMap<>();
    private final Map<String, Function<List<Expression>, Expression>> predefinedFunctions = new HashMap<>();

    SchemeContext() {
        var zero = new Atom<>((double) 0);
        var one = new Atom<>((double) 1);

        BinaryOperator<Expression> tmpPlus = (a, b) -> calculus(Double::sum, a, b);
        Function<List<Expression>, Expression> plus = args -> args.stream().reduce(zero, tmpPlus);

        BinaryOperator<Double> tmpm = (a, b) -> a * b;
        BinaryOperator<Expression> tmpMult = (a, b) -> calculus(tmpm, a, b);
        Function<List<Expression>, Expression> mult = args -> args.stream().reduce(one, tmpMult);

        BinaryOperator<Double> tmpd = (a, b) -> a / b;
        BinaryOperator<Expression> tmpDiv = (a, b) -> calculus(tmpm, a, b);
        Function<List<Expression>, Expression> div = args -> args.stream().reduce(one, tmpDiv);

        BinaryOperator<Double> tmps = (a, b) -> a - b;
        BinaryOperator<Expression> tmpSub = (a, b) -> calculus(tmpm, a, b);
        Function<List<Expression>, Expression> sub = args -> args.stream().reduce(zero, tmpSub);

        predefinedFunctions.put("*", mult);
        predefinedFunctions.put("-", sub);
        predefinedFunctions.put("+", plus);
        predefinedFunctions.put("/", div);

    }

    @Override
    public Expression lookup(String name) {
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
            res.addDefinition(variables.get(0), values.get(0));
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


    private <T> Expression calculus(BinaryOperator<T> f, Expression a, Expression b) {
        var val1 = (T) a.getNumericValue();
        var val2 = (T) b.getNumericValue();
        var res = f.apply(val1, val2);
        return new Atom<>(res);
    }

}
