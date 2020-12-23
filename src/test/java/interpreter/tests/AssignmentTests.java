package interpreter.tests;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Assignment;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeIdentifier;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;

public class AssignmentTests {

	@Test
	public void testEvaluateAssignment() {
		var assignmentForm = new Assignment(new SchemeIdentifier("foo"), new SchemeNumber(42));
		var executionContext = mock(ExecutionContext.class);
		var actualExpr = assignmentForm.evaluate(executionContext);
		var expectedExpr = new SchemeNumber(42);
		assertEquals(expectedExpr, actualExpr);
		verify(executionContext, times(1)).addDefinition(eq("foo"), eq(actualExpr));
	}

}
