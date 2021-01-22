package ru.nsu.fit.lispmachine.interpreter;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

//todo : move to homemade tests when parser will parse (try (body) (catch extype (catch-body)))
public class TryExceptTest {

    @Test
    public void testEvaluateNoException() {
        var prog = new TryExcept(new Application(new SchemeIdentifier("+"), List.of(new SchemeNumber(6), new SchemeNumber(2))),
                new SchemeNumber(12345));
        var executionContext = new SchemeContext();
        var actualExpr = prog.evaluate(executionContext);
        var expectedExpr = new SchemeNumber(8);
        assertEquals(expectedExpr, actualExpr);
    }

    @Test
    public void testEvaluateWithException() {
        var catchExpression = new SchemeNumber(12345);
        var prog = new TryExcept(new Application(new SchemeIdentifier("/"), List.of(new SchemeNumber(0), new SchemeNumber(0))),
                catchExpression);
        var executionContext = new SchemeContext();
        var actualExpr = prog.evaluate(executionContext);
        assertEquals(catchExpression, actualExpr);
    }

    @Test
    public void testEvaluateWithCustomException() {
        var catchExpression = new SchemeNumber(12345);
        var prog = new TryExcept(new Application(new SchemeIdentifier("throw"), List.of(new SchemeString("java.lang.IllegalArgumentException"), new SchemeString("look!"))),
                catchExpression);
        var executionContext = new SchemeContext();
        var actualExpr = prog.evaluate(executionContext);
        assertEquals(catchExpression, actualExpr);
    }

}
