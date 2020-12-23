package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.calculus;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Multiply extends NativeCall {
    @Override
    public Expression apply(List<Expression> arguments, ExecutionContext context) {
        var args =  arguments.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
        System.out.println("args = " + Arrays.toString(args.toArray()));

        if (! args.stream().allMatch(a-> a instanceof SchemeNumber)) {
            throw new IllegalArgumentException("* called with non numbers arguments");
        }
        if (args.stream().anyMatch(a-> ((SchemeNumber) a).getValue() instanceof Double)) {
            var res = args.stream().
                    map(a-> ((SchemeNumber) a).getValue()).
                    map(Number::doubleValue).
                    reduce(1., (a,b)-> a * b);
            return new SchemeNumber(res);
        } else {
            var res = args.stream().
                    map(a-> ((SchemeNumber) a).getValue()).
                    map(Number::intValue).
                    reduce(1,  (a,b)-> a * b);
            return new SchemeNumber(res);
        }
    }
}
