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
//        var base = " ((lambda (x) (+ 5 (* x x))) (+ 123 (* 5 (- 10 5) )) ) " +
//                " (define (area r) (* r r) ) " +
//                "(area 15)";
//
//        var parser = new Parser(Tokenizer.tokenize(base));
//        var exprs = parser.parse();
//        System.out.println("exprs = " + exprs);
//
//        for (Expression expr : exprs) {
//            System.out.println("expr = " + expr.evaluate(a));
//        }

        var x = new SchemerString("x");
        var factorial = new SchemerString("vactorial");
        var pred = new Application(new SchemerString("="), List.of(x, new SchemeNumber(0)));
        var trueb = new SchemeNumber(1);
        var mult = new SchemerString("*");

        var fact = new Application(factorial, List.of(new Application(new SchemerString("-"),List.of(x, trueb))));
        var falseb = new Application(mult, List.of(x, fact));

        var ifexp = new IfClause(pred, trueb, falseb);
        var define = new Define(factorial, List.of(x), ifexp);

        define.evaluate(a);

        System.out.println("Application(factorial, List.of(new SchemeNumber(10)) = " + new Application(factorial, List.of(new SchemeNumber(10))).evaluate(a));



    }
}
