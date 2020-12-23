package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.calculus;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeBool;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.List;

public class Less extends NativeCall {
    @Override
    public Expression apply(List<Expression> args, ExecutionContext context) {
        if (! args.stream().allMatch(a-> a instanceof SchemeNumber)) {
            throw new IllegalArgumentException("< called with non numbers arguments");
        }
        var first = ((SchemeNumber)args.get(0)).getValue();
        var res = true;
        for (Expression arg : args) {
            var var = ((SchemeNumber)arg).getValue();
            res = res && var.doubleValue() < first.doubleValue();
        }
        return new SchemeBool(res);
    }
}
