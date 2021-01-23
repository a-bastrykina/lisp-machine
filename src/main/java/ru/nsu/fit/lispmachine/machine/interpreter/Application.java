package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Application implements Expression {
    protected List<Expression> arguments;
    private final Expression operator;
    private ExecutionContext selfContext;

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
        Function<ExecutionContext, ExecutionContext> extend = e -> e.extendContext(arguments.stream().map(Objects::toString).collect(Collectors.toList()), applyArguments);
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
