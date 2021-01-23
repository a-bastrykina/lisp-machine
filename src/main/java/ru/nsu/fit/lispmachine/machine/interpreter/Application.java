package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A class to represent application of the Scheme language.
 */
public class Application implements Expression {
    protected List<Expression> arguments;
    private final Expression operator;
    private ExecutionContext selfContext;

    /**
     * @param operator
     * @param arguments
     * @throws NullPointerException if arguments are null
     */
    public Application(Expression operator, List<Expression> arguments) {
        this.operator = Objects.requireNonNull(operator);
        this.arguments = Objects.requireNonNull(arguments);
    }

    public Application(Expression operator, List<Expression> arguments, ExecutionContext specialContext) {
        this.operator = Objects.requireNonNull(operator);
        this.arguments = Objects.requireNonNull(arguments);
        this.selfContext = specialContext;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        if (!context.isLazyModelSupported()) {
            var op = operator.evaluate(context);
            return op.apply(arguments.stream().map(e -> e.evaluate(context)).collect(Collectors.toList()), context);
        }
        var op = context.getActualExpressionValue(operator);
        return op.apply(arguments, context);
    }

    @Override
    public Expression apply(List<Expression> applyArguments, ExecutionContext context) {
        if (!context.isLazyModelSupported()) {
            Function<ExecutionContext, ExecutionContext> extend = e -> e.extendContext(arguments.stream().map(Objects::toString).collect(Collectors.toList()), applyArguments);
            var newContext = (selfContext != null) ? extend.apply(selfContext) : extend.apply(context);
            return this.operator.evaluate(newContext);
        }
        Function<ExecutionContext, ExecutionContext> extend = e -> {
            var thunks = new ArrayList<Expression>();
            for (Expression argument : applyArguments) {
                thunks.add(new Thunk(argument, context));
            }
            return e.extendContext(arguments.stream().map(Objects::toString).collect(Collectors.toList()), thunks);
        };
        var newContext = (selfContext != null) ? extend.apply(selfContext) : extend.apply(context);
        return this.operator.evaluate(newContext);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(arguments, that.arguments) && Objects.equals(operator, that.operator) && Objects.equals(selfContext, that.selfContext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arguments, operator, selfContext);
    }
}
