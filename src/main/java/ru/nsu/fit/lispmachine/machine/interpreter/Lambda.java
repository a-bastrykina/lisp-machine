package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;

public class Lambda implements Expression {
    private final List<Expression> parameters;
    private final Expression body;

    Lambda(List<Expression> parameters, Expression body) {
//        (lambda (r) (* r r)) -> parameter=[String(r)], body=Application(Operator=Symbol(*), Args =[String(r), String(r)]
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return new Application(body, parameters, context);
    }

}
