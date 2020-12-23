package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application implements Expression {
    protected List<Expression> arguments;
    protected ExecutionContext context;
    private final Expression operator;

    public Application(Expression operator, List<Expression> arguments) {

        // допустим парсим (+ 5 5)
        // это application, у которого operator=String(+), Arguments = [Number(5), Number(5)]

//        допустим парсим ((lambda (r) (* r r)) 5)

        // это application,  у которого оператор это lambda(args= [String(r)], body = Application...), args = Number(5)
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        var op = operator.evaluate(context);
        var args = arguments.stream().map(a-> a.evaluate(context)).collect(Collectors.toList());
        return op.apply(args, context);
    }

    @Override
    public Expression apply(List<Expression> args, ExecutionContext context) {
        //todo fix me!
        throw new IllegalArgumentException("Fix apply in Application.java!");
    }
}
