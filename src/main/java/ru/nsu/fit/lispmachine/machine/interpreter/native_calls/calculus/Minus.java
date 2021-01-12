package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.calculus;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Minus extends NativeCall {
    @Override
    public Expression apply(List<Expression> arguments, ExecutionContext context) {
        var args = arguments.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
        if (args.size() < 1) {
            throw new IllegalArgumentException("- called without arguments");
        }
        if (args.size() == 1) {
            var newArgs = new ArrayList<Expression>();
            newArgs.add(new SchemeNumber(0));
            newArgs.add(arguments.get(0));
            return new Minus().apply(newArgs, context);
        }

        if (!args.stream().allMatch(a -> a instanceof SchemeNumber)) {
            throw new IllegalArgumentException("- called with non numbers arguments");
        }

        var headValue = ((SchemeNumber) args.get(0)).getValue().doubleValue();
        for (Expression arg : args.stream().skip(1).collect(Collectors.toList())) {
            var val = ((SchemeNumber) arg).getValue().doubleValue();
            headValue -= val;
        }
        if (args.stream().anyMatch(a -> ((SchemeNumber) a).getValue() instanceof Double)) {
            return new SchemeNumber(headValue);
        } else {
            return new SchemeNumber((int) headValue);
        }

    }
}
