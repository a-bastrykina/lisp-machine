package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.DottedSchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsCall extends NativeCall {
    @Override
    public Expression apply(List<Expression> args, ExecutionContext context) {
        if (context.isLazyModelSupported()) {
            args = args.stream().map(context::getActualExpressionValue).collect(Collectors.toList());
        }
        if (args.size() != 2) {
            throw new IllegalArgumentException("cons requires 2 arguments");
        }
        var tmp = new ArrayList<Expression>();
        if (args.get(1) instanceof SchemeList) {
            tmp.add(args.get(0));
            tmp.addAll(((SchemeList) args.get(1)).getValues());
            return new SchemeList(tmp);
        }
        tmp.add(args.get(0));
        tmp.add(args.get(1));
        return new DottedSchemeList(tmp);
    }
}
