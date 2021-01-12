package interpreter.tests;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Define;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeIdentifier;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeString;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DefineTests {

	@Test
	public void testEvaluateDefine() {
		var defineForm = new Define(new SchemeIdentifier("my-name"), new SchemeString("Alena"));
		var executionContext = mock(ExecutionContext.class);
		var actualExpr = defineForm.evaluate(executionContext);
		var expectedExpr = new SchemeString("Alena");
		assertEquals(expectedExpr, actualExpr);
		verify(executionContext, times(1)).addDefinition(eq("my-name"), eq(actualExpr));
	}

}
