package ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.NativeCall;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarCall extends NativeCall {
    @Override
    public Expression apply(List<Expression> args, ExecutionContext context) {
        if (context.isLazyModelSupported()) {
            args = args.stream().map(context::getActualExpressionValue).collect(Collectors.toList());
        }
        if (args.size() != 1) {
            throw new IllegalArgumentException("car requires 1 argument");
        }
        if (!(args.get(0) instanceof SchemeList)) {
            throw new IllegalArgumentException("car requires list as argument");
        }
        var tmp = ((SchemeList) args.get(0)).getValues();
        if (tmp.size() == 0) {
            throw new IllegalArgumentException("car on empty list");
        }
        return tmp.get(0);
    }
}
