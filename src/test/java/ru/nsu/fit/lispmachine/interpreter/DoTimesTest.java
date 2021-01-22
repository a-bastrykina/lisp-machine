package ru.nsu.fit.lispmachine.interpreter;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.*;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.lists.ConsCall;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DoTimesTest {

	@Test
	public void testEvaluateDefine() {

	    //: todo move to HomeMadeTests when parser (do-times (var upper-bound) (body)) will ready
	    var loopVar = new SchemeIdentifier("i");
		var defineForm = new DoTimes(loopVar, new SchemeNumber(10), new Application(new SchemeIdentifier("cons"), List.of(loopVar, loopVar)));
		var executionContext = new SchemeContext();
		var actualExpr = defineForm.evaluate(executionContext);
        assertEquals("(9 . 9)", actualExpr.toString());
	}

}
