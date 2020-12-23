package interpreter.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeChar;

public class SchemeCharTests {
	@Test
	public void testEvaluate() {
		SchemeChar schemeChar = new SchemeChar('a');
		assertEquals(schemeChar, schemeChar.evaluate(mock(ExecutionContext.class)));
	}
}
