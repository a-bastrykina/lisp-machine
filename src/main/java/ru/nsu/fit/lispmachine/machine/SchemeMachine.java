package ru.nsu.fit.lispmachine.machine;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;

import java.io.IOException;
import java.util.stream.Collectors;

public class SchemeMachine {
    private String stdLibrary = "(define (display obj) (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"writeString\" obj))";
    private ExecutionContext context = new SchemeContext();

    SchemeMachine() {
        var exprs = new Parser(Tokenizer.tokenize(stdLibrary)).parse();
        exprs = exprs.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
    }

    Expression execLine(String line) {
        var exprs = new Parser(Tokenizer.tokenize(line)).parse();
        exprs = exprs.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
        return exprs.get(exprs.size() - 1);
    }

    public static void main(String[] args) throws IOException {
        var m = new SchemeMachine();
        while (true) {
            var st = SchemeMachineUtils.readString();
            var res = m.execLine(st);
            if (res != null)
                System.out.println(m.execLine(st));
        }
    }

}

