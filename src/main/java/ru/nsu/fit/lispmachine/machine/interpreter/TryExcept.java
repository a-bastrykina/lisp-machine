package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.List;
import java.util.Objects;

public class TryExcept implements Expression {

    private final Expression body;
    private final List<SchemeIdentifier> exceptionNames;
    private final List<Expression> catchBodies;

    public TryExcept(Expression body, List<SchemeIdentifier> exceptionNames, List<Expression> catchBodies) {
        this.body = body;
        this.exceptionNames = exceptionNames;
        this.catchBodies = catchBodies;
    }

	@Override
	public Expression evaluate(ExecutionContext context) {
        try {
            return body.evaluate(context);
        }
        catch (Exception exc) {
            for (int i = 0; i < exceptionNames.size(); i++) {
                if (("class " + exceptionNames.get(i).toString()).equals(exc.getClass().toString())) {
                    return catchBodies.get(i).evaluate(context);
                }
            }
            throw new IllegalArgumentException(exc);
        }
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TryExcept tryExcept = (TryExcept) o;
        return Objects.equals(body, tryExcept.body) && Objects.equals(exceptionNames, tryExcept.exceptionNames) && Objects.equals(catchBodies, tryExcept.catchBodies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, exceptionNames, catchBodies);
    }
}
