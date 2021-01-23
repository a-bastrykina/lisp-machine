package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.calculus;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.List;
import java.util.stream.Collectors;

public class Mod extends NativeCall {
    @Override
    public Expression apply(List<Expression> arguments, ExecutionContext context) {
        var args = arguments.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());

        if (!args.stream().allMatch(a -> a instanceof SchemeNumber)) {
            throw new IllegalArgumentException("% called with non numbers arguments");
        }
        if (args.size() != 2) {
            throw new IllegalArgumentException("% must be called with 2 arguments");
        }

        var a = (int) args.get(0).castTo("java.lang.Int");
        var b = (int) args.get(1).castTo("java.lang.Int");
        return new SchemeNumber(a % b);
    }
}
