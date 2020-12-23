package ru.nsu.fit.lispmachine.machine;

import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Application;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.RawString;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;

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
        res = d.evaluate(a);
        System.out.println("res = " + res);
    }
}
