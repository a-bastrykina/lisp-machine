package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.calculus;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.List;
import java.util.stream.Collectors;

public class Division extends NativeCall {
    @Override
    public Expression apply(List<Expression> args, ExecutionContext context) {

        if (!args.stream().allMatch(a -> a instanceof SchemeNumber)) {
            throw new IllegalArgumentException("/ called with non numbers arguments");
        }
        if (args.stream().anyMatch(a -> ((SchemeNumber) a).getValue() instanceof Double)) {
            var res = args.stream().
                    map(a -> ((SchemeNumber) a).getValue()).
                    map(Number::doubleValue).
                    reduce(1., (a, b) -> a / b);
            return new SchemeNumber(res);
        } else {
            int res = (int) args.get(0).castTo("java.lang.Int");
            for (Expression arg : args.stream().skip(1).collect(Collectors.toList())) {
                res /= (int) arg.castTo("java.lang.Int");
            }
            return new SchemeNumber(res);
        }
    }
}
