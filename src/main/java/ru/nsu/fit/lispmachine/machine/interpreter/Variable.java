//package ru.nsu.fit.lispmachine.machine.interpreter;
//
//import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
//
//import java.util.List;
//
//public class Variable implements Expression {
//
//    private String name;
//
//    Variable(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public Expression evaluate(ExecutionContext context) {
//        return context.lookupVariable(name);
//    }
//
//    @Override
//    public List<Expression> getArguments() {
//        return null;
//    }
//}
