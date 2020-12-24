package interpreter.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Assignment;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeIdentifier;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;

public class SchemeIdentifierTests {
	@Test
	public void testEvaluateIdentifier() {
		var assignmentForm = new SchemeIdentifier("foo");
		var executionContext = mock(ExecutionContext.class);
		when(executionContext.lookupVariable(eq("foo"))).thenReturn(new SchemeNumber(42));
		var actualExpr = assignmentForm.evaluate(executionContext);
		var expectedExpr = new SchemeNumber(42);
		assertEquals(expectedExpr, actualExpr);

	}
}
