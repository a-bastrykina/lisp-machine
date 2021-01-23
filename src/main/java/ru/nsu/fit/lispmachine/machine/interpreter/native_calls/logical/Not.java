package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeBool;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.List;
import java.util.stream.Collectors;

public class Not extends NativeCall {
    @Override
    public Expression apply(List<Expression> args, ExecutionContext context) {
        if (context.isLazyModelSupported()) {
            args = args.stream().map(context::getActualExpressionValue).collect(Collectors.toList());
        }
        if (args.size() != 1) {
            throw new IllegalArgumentException("not required only 1 argument");
        }
        return new SchemeBool(!(boolean) args.get(0).castTo("java.lang.Boolean"));
    }
}
