package interpreter.tests;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.IfClause;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeBool;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;

public class IfClauseTests {

	@Test
	public void testEvaluateIf_WithTrueCond() {
		var ifForm = new IfClause(new SchemeBool(true), new SchemeNumber(1), new SchemeNumber(2));
		var executionContext = mock(ExecutionContext.class);
		var actualExpr = ifForm.evaluate(executionContext);
		var expectedExpr = new SchemeNumber(1);
		assertEquals(expectedExpr, actualExpr);
	}

	@Test
	public void testEvaluateIf_WithFalseCond() {
		var ifForm = new IfClause(new SchemeBool(false), new SchemeNumber(1), new SchemeNumber(2));
		var executionContext = mock(ExecutionContext.class);
		var actualExpr = ifForm.evaluate(executionContext);
		var expectedExpr = new SchemeNumber(2);
		assertEquals(expectedExpr, actualExpr);
	}
}
