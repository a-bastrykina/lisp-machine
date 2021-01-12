package interpreter.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;

public class SchemeNumberTests {
	@Test
	public void testCreateNumber() {
		var num1 = new SchemeNumber(1);
		assertEquals(1, num1.getValue());
		var num2 = new SchemeNumber(1.0);
		assertEquals(1.0, (Double) num2.getValue(), 0.001);
	}

	@Test
	public void testEvaluateNumber() {
		var schemeNumber = new SchemeNumber(1);
		assertEquals(schemeNumber, schemeNumber.evaluate(mock(ExecutionContext.class)));
	}
}
