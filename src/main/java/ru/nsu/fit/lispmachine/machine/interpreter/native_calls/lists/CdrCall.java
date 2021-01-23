package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.DottedSchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CdrCall extends NativeCall {
    @Override
    public Expression apply(List<Expression> arguments, ExecutionContext context) {
        var args = arguments.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
        if (args.size() != 1) {
            throw new IllegalArgumentException("cdr requires 1 argument");
        }
        if (!(args.get(0) instanceof SchemeList)) {
            throw new IllegalArgumentException("cdr requires list as argument");
        }
        if (args.get(0) instanceof DottedSchemeList) {
            return ((DottedSchemeList)args.get(0)).getValues().get(1);
        }
        var tmp = new ArrayList<>(((SchemeList) args.get(0)).getValues());
        if (tmp.size() == 0) {
            throw new IllegalArgumentException("cdr on empty list");
        }
        tmp.remove(0);
        return new SchemeList(tmp);
    }
}
