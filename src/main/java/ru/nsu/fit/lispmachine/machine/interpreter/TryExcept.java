package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.Objects;

public class TryExcept implements Expression {

    private final Expression body;
    private final Expression catchBody;

    public TryExcept(Expression body, Expression catchBody) {
        this.body = body;
        this.catchBody = catchBody;
    }

	@Override
	public Expression evaluate(ExecutionContext context) {
        try {
            return body.evaluate(context);
        }
        catch (Exception exc) {
            return catchBody.evaluate(context);
        }
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TryExcept tryExcept = (TryExcept) o;
        return Objects.equals(body, tryExcept.body) && Objects.equals(catchBody, tryExcept.catchBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, catchBody);
    }
}
