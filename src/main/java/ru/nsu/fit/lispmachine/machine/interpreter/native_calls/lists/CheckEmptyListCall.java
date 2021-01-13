package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeBool;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.List;
import java.util.stream.Collectors;

public class CheckEmptyListCall extends NativeCall {
    @Override
    public Expression apply(List<Expression> arguments, ExecutionContext context) {
        var args = arguments.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
        if (args.size() != 1) {
            throw new IllegalArgumentException("null? requires only one argument");
        }
        var check = args.get(0) instanceof SchemeList && !args.get(0).isTrue();
        return new SchemeBool(check);
    }
}
