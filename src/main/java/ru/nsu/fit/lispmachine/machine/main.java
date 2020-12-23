package ru.nsu.fit.lispmachine.machine;

import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.*;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.calculus.Equal;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        var a = new SchemeContext();
//        (+ 123 (* 5 (- 10 5) ))
        var base = " ((lambda (x) (* x x)) (+ 123 0) )";
        var parser = new Parser(Tokenizer.tokenize(base));
        var exprs = parser.parse();
        System.out.println("exprs = " + exprs);
        for (Expression expr : exprs) {
            System.out.println("expr = " + expr.evaluate(a));
        }

        var lambdaApplyer = "";

    }
}
