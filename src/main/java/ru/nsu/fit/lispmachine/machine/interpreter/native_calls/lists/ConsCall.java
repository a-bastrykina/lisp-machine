package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsCall extends NativeCall {
    @Override
    public Expression apply(List<Expression> arguments, ExecutionContext context) {
        var args = arguments.stream().map(e-> e.evaluate(context)).collect(Collectors.toList());
        if (args.size() != 2) {
            throw new IllegalArgumentException("cons requires 2 arguments");
        }
        var tmp = new ArrayList<Expression>();
        tmp.add(args.get(0));
        tmp.add(args.get(1));
        return new SchemeList(tmp);
    }
}
