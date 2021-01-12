package ru.nsu.fit.lispmachine.machine;

import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.*;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.JavaMethodCall;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var a = new SchemeContext();
        var base = " " +
                "((lambda (x) (+ 5 (* x x))) (+ 123 (* 5 (- 10 5) )) ) " +
                " (define (area r) (* r r) ) " +
                "(area 15)" +
                "(define (factorial x) (if (= x 0)  1 (* x (factorial (- x 1)))))" +
                "(factorial 10)" +
                "(define (fib x) (if (< x 2) 1 (+ (fib (- x 1)) (fib (- x 2)))))"
                + "(fib 10)"
                + "(list 1 2 3 +)"
                + "(cons 1 2)"
                + ("(cons 1 (list 5 6))")
                + "(define (toint r) (if r 1 0 ))"
                + "(define (count item l) (if l (+ (toint (= item (car l))) (count item (cdr l))) 0))"
                + "(count 0 (list 0 1 2 3 0 0))"
                + "(define r2 10)"
                + "(set! r2 (lambda (r) (* r r)))"
                + " r2";
        ;
        //
        var parser = new Parser(Tokenizer.tokenize(base));
        var exprs = parser.parse();
        ////
        for (Expression expr : exprs) {
            System.out.println("expr = " + expr.evaluate(a));
        }

        // (java-call "java.lang.Double" "sum" 1 2)
        //parser not yet done
        var static_obj = "java.lang.Double";
        var methodname = "sum";
        List<Expression> ars = List.of(new SchemeNumber(123), new SchemeNumber(14));
        var javaCall = new JavaMethodCall(new SchemeString(static_obj), new SchemeString(methodname), ars);
        System.out.println(javaCall.evaluate(a));

    }
}
