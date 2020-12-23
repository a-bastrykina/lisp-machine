package interpreter.tests;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Application;
import ru.nsu.fit.lispmachine.machine.interpreter.Define;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeIdentifier;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeString;

public class DefineTests {

	@Test
	public void testEvaluateDefine() {
		var defineForm = new Define(new SchemeIdentifier("my-name"), new SchemeString("Alena"));
		var executionContext = mock(ExecutionContext.class);
		var actualExpr = defineForm.evaluate(executionContext);
		var expectedExpr = new Application(new SchemeString("Alena"), Collections.emptyList());
		assertEquals(expectedExpr, actualExpr);
		verify(executionContext, times(1)).addDefinition(eq("my-name"), eq(actualExpr));
	}

}
