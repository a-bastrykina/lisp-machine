package ru.nsu.fit.lispmachine.machine.expression.evaluator;

import ru.nsu.fit.lispmachine.machine.expression.Expression;
import ru.nsu.fit.lispmachine.machine.expression.evaluator.strategies.EvaluationStrategy;

public abstract class ExpressionEvaluator {
    private final EvaluationStrategy strategy;

    public ExpressionEvaluator(EvaluationStrategy strategy) {
        this.strategy = strategy;
    }

    Expression evaluate(Expression exp) {
        return strategy.eval(exp);
    }
}
