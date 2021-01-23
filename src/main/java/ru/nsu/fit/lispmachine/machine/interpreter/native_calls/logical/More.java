package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeBool;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.List;
import java.util.stream.Collectors;

public class More extends NativeCall {
    @Override
    public Expression apply(List<Expression> args, ExecutionContext context) {
        if (context.isLazyModelSupported()) {
            args = args.stream().map(context::getActualExpressionValue).collect(Collectors.toList());
        }
        if (!args.stream().allMatch(a -> a instanceof SchemeNumber)) {
            throw new IllegalArgumentException("> called with non numbers arguments");
        }
        var first = ((SchemeNumber) args.get(0)).getValue();
        var res = true;
        for (Expression arg : args.stream().skip(1).collect(Collectors.toList())) {
            var var = ((SchemeNumber) arg).getValue();
            res = res && first.doubleValue() > var.doubleValue();
        }
        return new SchemeBool(res);
    }
}
