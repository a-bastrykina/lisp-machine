package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public class Plus extends Application  {

    public Plus(Expression operator, List<Expression> arguments, ExecutionContext context) {
        super(operator, arguments, context);
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        var evaluated = this.arguments.stream().map(e->e.evaluate(context));

        return null;
    }

}
