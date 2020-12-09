package ru.nsu.fit.lispmachine.machine.expression;

import ru.nsu.fit.lispmachine.machine.expression.types.ExpressionType;

import java.util.List;

public interface Expression {
    ExpressionType getType();
    List<Expression> getArguments();
}
