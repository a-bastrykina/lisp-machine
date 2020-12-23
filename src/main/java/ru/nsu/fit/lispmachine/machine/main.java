package ru.nsu.fit.lispmachine.machine;

import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.*;

import java.util.List;

public class main {
    public static void main(String[] args) {
        var a = new SchemeContext();
        List<Expression> ars = List.of(
                new SchemeNumber(10.123),
                new SchemeNumber(5),
                new SchemeNumber(15)
        );
        var d = new Application( new RawString("+"), ars);
        var res = d.evaluate(a);
        System.out.println("res = " + res);

        List<Expression> ars1 = List.of(
                new SchemeNumber(10.123),
                new SchemeNumber(5),
                new SchemeNumber(15),
                new Application( new RawString("+"), List.of(new SchemeNumber(1)))
        );

        d = new Application( new RawString("+"), ars1);
        var res1 = d.evaluate(a);
        System.out.println("res1 = " + res1);

        d = new Application(new RawString("not"),
                List.of(new Application(new RawString("="), List.of(res, res1)))
        );
        System.out.println("d = " + d.evaluate(a));

        var ifCheck = new IfClause(new Application(new RawString("not"), List.of(d.evaluate(a))), new Application( new RawString("+"), ars1), new SchemeNumber(123) );
        System.out.println("ifCheck = " + ifCheck.evaluate(a));
    }
}
