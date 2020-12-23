//package ru.nsu.fit.lispmachine.machine.interpreter;
//
//import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
//
//import java.util.List;
//
//public class IfClause implements Expression {
//
//    private final Expression pred;
//    private final Expression trueBody;
//    private final Expression falseBody;
//
//    IfClause(Expression pred, Expression trueBody, Expression falseBody) {
//        this.pred = pred;
//        this.trueBody = trueBody;
//        this.falseBody = falseBody;
//    }
//
//    @Override
//    public Expression evaluate(ExecutionContext context) {
//        return pred.evaluate(context).isTrue() ? trueBody.evaluate(context) : falseBody.evaluate(context);
//    }
//
//    @Override
//    public List<Expression> getArguments() {
//        return List.of(pred, trueBody, falseBody);
//    }
//}
