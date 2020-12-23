package interpreter.tests;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Begin;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;

public class BeginTests {
	@Test
	public void testEvaluateBegin() {
		var beginForm = new Begin(List.of(new SchemeNumber(1), new SchemeNumber(2), new SchemeNumber(3)));
		var executionContext = mock(ExecutionContext.class);
		var actualExpr = beginForm.evaluate(executionContext);
		var expectedExpr = new SchemeNumber(3);
		assertEquals(expectedExpr, actualExpr);
	}
}
