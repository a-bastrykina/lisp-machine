package ru.nsu.fit.lispmachine.interpreter;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TryExceptTest {

	@Test
	public void testEvaluateNoException() {
		var body = new Application(new SchemeIdentifier("+"), List.of(new SchemeNumber(6), new SchemeNumber(2)));
		var prog = new TryExcept(body,
				Map.of(new SchemeString("java.lang.DivisionByZero"), new SchemeNumber(12345)));
		var executionContext = new SchemeContext();
		var actualExpr = prog.evaluate(executionContext);
		var expectedExpr = new SchemeNumber(8);
		assertEquals(expectedExpr, actualExpr);
	}

	@Test
	public void testEvaluateWithException() {
		var body = new Application(new SchemeIdentifier("/"), List.of(new SchemeNumber(0), new SchemeNumber(0)));
		var catchExpression = new SchemeNumber(12345);
		var prog = new TryExcept(body, Map.of(new SchemeString("java.lang.ArithmeticException"), catchExpression));
		var executionContext = new SchemeContext();
		var actualExpr = prog.evaluate(executionContext);
		assertEquals(catchExpression, actualExpr);
	}


	//todo: uncomment this
	//    @Test
	//    public void testEvaluateWithCustomException() {
	//        var catchExpression = new SchemeNumber(12345);
	//        var prog = new TryExcept(new Application(new SchemeIdentifier("throw"), List.of(new SchemeString("java.lang.IllegalArgumentException"), new SchemeString("look!"))),
	//                catchExpression);
	//        var executionContext = new SchemeContext();
	//        var actualExpr = prog.evaluate(executionContext);
	//        assertEquals(catchExpression, actualExpr);
	//    }

}
