package ru.nsu.fit.lispmachine.machine.expression.evaluator.strategies;

import ru.nsu.fit.lispmachine.machine.expression.Expression;

public interface EvaluationStrategy {
    Expression eval(Expression exp);
}
