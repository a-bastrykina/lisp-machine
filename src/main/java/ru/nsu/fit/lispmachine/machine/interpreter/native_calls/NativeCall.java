package ru.nsu.fit.lispmachine.machine.interpreter.native_calls;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;

public abstract class NativeCall implements Expression {

    @Override
    public Expression evaluate(ExecutionContext context) {
        throw new IllegalArgumentException("evaluate in native call!");
    }
}
