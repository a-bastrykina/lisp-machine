package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
		this.operator = Objects.requireNonNull(operator);
		this.arguments = Objects.requireNonNull(arguments);
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		var op = operator.evaluate(context);
		return op.apply(arguments, context);
	}

	@Override
	public Expression apply(List<Expression> applyArguments, ExecutionContext context) {
		var args = applyArguments.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
		System.out.println("arguments = " + Arrays.toString(arguments.toArray()));
		var newContext = context
				.extendContext(arguments.stream().map(Objects::toString).collect(Collectors.toList()), args);
		return this.operator.evaluate(newContext);
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Application that = (Application) o;
		return Objects.equals(arguments, that.arguments) &&
				Objects.equals(operator, that.operator);
	}

	@Override public int hashCode() {
		return Objects.hash(arguments, operator);
	}
}
