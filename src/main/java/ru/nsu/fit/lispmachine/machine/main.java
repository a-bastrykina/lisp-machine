package ru.nsu.fit.lispmachine.machine;

import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.*;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.logical.calculus.Equal;

import java.util.List;

public class main {
    public static void main(String[] args) {
        var a = new SchemeContext();
        List<Expression> ars = List.of(
                new SchemeNumber(10.123),
                new SchemeNumber(5),
                new SchemeNumber(15)
        );
        var d = new Application( new SchemerString("+"), ars);
        var res = d.evaluate(a);
        System.out.println("res = " + res);

        List<Expression> ars1 = List.of(
                new SchemeNumber(10.123),
                new SchemeNumber(5),
                new SchemeNumber(15),
                new Application( new SchemerString("+"), List.of(new SchemeNumber(1)))
        );

        d = new Application( new SchemerString("+"), ars1);
        var res1 = d.evaluate(a);
        System.out.println("res1 = " + res1);

        d = new Application(new SchemerString("not"),
                List.of(new Application(new SchemerString("="), List.of(res, res1)))
        );
        System.out.println("d = " + d.evaluate(a));

        var ifCheck = new IfClause(new Application(new SchemerString("not"), List.of(d.evaluate(a))), new Application( new SchemerString("+"), ars1), new SchemeNumber(123) );
        System.out.println("ifCheck = " + ifCheck.evaluate(a));

        // factorial check here;
//        (define (factorial x) (if (= x 0)  1 (* x (factorial (- x 1)))))

        var factBody = new IfClause( new Application(new SchemerString("="), List.of(new SchemeNumber(0), new SchemerString("x"))),
                                    new SchemeNumber(0),
                                    new Application(new SchemerString("*"),
                                            List.of(new SchemerString("x"),
                                                    new Application( new SchemerString("factorial"),
                                                            List.of(new Application(new SchemerString("-"), List.of(new SchemerString("x"),
                                                                    new SchemeNumber(0))))))));


//        ((lambda (r) (* r r)) 5)
        var body = new Application( new Lambda(List.of(new SchemerString("x")),
                new Application( new SchemerString("*"), List.of(new SchemerString("x"),new SchemerString("x")) )),
                List.of(new SchemeNumber(5)));

        System.out.println(body.evaluate(a));



        try {
            var define = new Define(new SchemerString("factorial"), List.of(new SchemerString("x")), factBody);

            System.out.println("define = " + define.evaluate(a));
            System.out.println("factorial = " + new Application(define.evaluate(a), List.of(new SchemeNumber(10))).evaluate(a));

        }
        catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("e = " + e.getMessage());
        }




//        (define circle-area (lambda (r) (* pi (* r r))))



    }
}
