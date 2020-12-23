package ru.nsu.fit.lispmachine.machine.interpreter.native_calls;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;

import java.util.List;

public class Plus extends NativeCall {
    @Override
    public Expression apply(List<Expression> args, ExecutionContext context) {
        if (! args.stream().allMatch(a-> a instanceof SchemeNumber)) {
            throw new IllegalArgumentException("+ called with non numbers arguments");
        }
        if (args.stream().anyMatch(a-> ((SchemeNumber) a).getValue() instanceof Double)) {
            var res = args.stream().
                    map(a-> ((SchemeNumber) a).getValue()).
                    map(Number::doubleValue).
                    reduce(0., Double::sum);
            return new SchemeNumber(res);
        } else {
            var res = args.stream().
                    map(a-> ((SchemeNumber) a).getValue()).
                    map(Number::intValue).
                    reduce(0, Integer::sum);
            return new SchemeNumber(res);
        }
    }
}
