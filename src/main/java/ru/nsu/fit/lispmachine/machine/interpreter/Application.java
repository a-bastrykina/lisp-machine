package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Application implements Expression {
	protected List<Expression> arguments;
	protected ExecutionContext context;
	private final Expression operator;

	public Application(Expression operator, List<Expression> arguments, ExecutionContext context) {

		// допустим парсим (+ 5 5)
		// это application, у которого operator=String(+), Arguments = [Number(5), Number(5)]

		//        допустим парсим ((lambda (r) (* r r)) 5)
		// это application,  у которого оператор это lambda(args= [String(r)], body = Application...), args = Number(5)
		this.operator = operator;
		this.arguments = arguments;
	}

	@Override
	public Expression evaluate(ExecutionContext context) {
		return null;
	}

	//    @Override
	//    public Expression evaluate(ExecutionContext c) {
	//        return apply(operator.evaluate(c), arguments, c);
	//    }
	//
	//    @Override
	//    public List<Expression> getArguments() {
	//        return new ArrayList<>();
	//    }
	//
	//    private Expression apply(Expression body, List<Expression> args, ExecutionContext c) {
	//        var evaluatedArgs = args.stream().map(e -> e.evaluate(c)).collect(Collectors.toList());
	//        return body.evaluate(evaluatedArgs, context);
	//    }

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
