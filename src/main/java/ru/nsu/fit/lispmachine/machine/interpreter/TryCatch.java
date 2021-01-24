package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.Map;
import java.util.Objects;

/**
 * Class to represent try-catch clause
 * Example in Scheme:
 * (try-catch (/ 1 0) "java.lang.ArithmeticException" (println "Oops"))
 */
public class TryCatch implements Expression {

	private final Expression body;
	private final Map<SchemeString, Expression> exceptionsAndCatches;

	/**
	 * @param body an expression of try block
	 * @param exceptionsAndCatches a map of exception types and catch expressions
	 * @throws NullPointerException if arguments are null
	 */
	public TryCatch(Expression body, Map<SchemeString, Expression> exceptionsAndCatches) {
		this.body = Objects.requireNonNull(body);
		this.exceptionsAndCatches = Objects.requireNonNull(exceptionsAndCatches);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		try {
			return body.evaluate(context);
		} catch (Exception exc) {
			var excName = exc.getClass().getName();
			var catchBody = exceptionsAndCatches.get(new SchemeString(excName));
			if (catchBody != null) {
				return catchBody.evaluate(context);
			}
			throw new IllegalArgumentException("Could find matching catch clause for " + exc.getClass().getName(), exc);
		}
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TryCatch tryCatch = (TryCatch) o;
		return body.equals(tryCatch.body) &&
				exceptionsAndCatches.equals(tryCatch.exceptionsAndCatches);
	}

	@Override public int hashCode() {
		return Objects.hash(body, exceptionsAndCatches);
	}
}
