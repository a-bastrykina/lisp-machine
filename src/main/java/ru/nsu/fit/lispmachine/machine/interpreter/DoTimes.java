package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DoTimes implements Expression {


    private final SchemeIdentifier variable;
    private final SchemeNumber upperLimit;
    private final Expression body;

    public DoTimes(SchemeIdentifier variable, SchemeNumber upperLimit, Expression body) {
        this.variable = variable;
        this.upperLimit = upperLimit;
        this.body = body;
    }

	@Override
	public Expression evaluate(ExecutionContext context) {
        Expression result = new SchemeList(new ArrayList<>());
        for (int i = 0; i <(int) upperLimit.castTo("java.lang.Int"); i++) {
            var currentIter = new SchemeNumber(i);
            var withLoopVar = context.extendContext(List.of(variable.toString()), List.of(currentIter));
            result = body.evaluate(withLoopVar);
        }
        return result;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoTimes doTimes = (DoTimes) o;
        return Objects.equals(variable, doTimes.variable) && Objects.equals(upperLimit, doTimes.upperLimit) && Objects.equals(body, doTimes.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable, upperLimit, body);
    }
}
