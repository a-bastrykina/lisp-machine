package ru.nsu.fit.lispmachine.interpreter;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CondClauseTests {

    //todo: move to homemadetests
	@Test
	public void testEvaluatefirstCond() {
//	    "(cond ((> 3 2) 'greater)\n" +
//                "      ((< 3 2) 'less))"
        var gt = new Application(new SchemeIdentifier(">"), List.of(new SchemeNumber(3), new SchemeNumber(2)));
        var lt = new Application(new SchemeIdentifier("<"), List.of(new SchemeNumber(3), new SchemeNumber(2)));
        var greater = new QuotedExpr(new SchemeString("greater"));
        var less = new QuotedExpr(new SchemeString("less"));

        var clause1 = new SchemeList(List.of(gt, greater));
        var clause2 = new SchemeList(List.of(lt, less));

        var ifForm = new CondClause(List.of(clause1, clause2), null);
		var executionContext = new SchemeContext();
		var actualExpr = ifForm.evaluate(executionContext);
		var expectedExpr = new SchemeString("greater");
		assertEquals(expectedExpr, actualExpr);
	}

	@Test
	public void testEvaluateSecondCond() {
//        (cond ((> 3 3) 'greater)
//        ((< 3 5) 'less)
//        (else 'equal))

        var gt = new Application(new SchemeIdentifier(">"), List.of(new SchemeNumber(3), new SchemeNumber(3)));
        var lt = new Application(new SchemeIdentifier("<"), List.of(new SchemeNumber(3), new SchemeNumber(5)));
        var greater = new QuotedExpr(new SchemeString("greater"));
        var less = new QuotedExpr(new SchemeString("less"));

        var clause1 = new SchemeList(List.of(gt, greater));
        var clause2 = new SchemeList(List.of(lt, less));

        var ifForm = new CondClause(List.of(clause1, clause2), new QuotedExpr(new SchemeString("equal")));
        var executionContext = new SchemeContext();
        var actualExpr = ifForm.evaluate(executionContext);
        var expectedExpr = new SchemeString("less");
        assertEquals(expectedExpr, actualExpr);
	}

    @Test
    public void testEvaluateElseCond() {
//        (cond ((> 3 3) 'greater)
//        ((< 3 3) 'less)
//        (else 'equal))

        var gt = new Application(new SchemeIdentifier(">"), List.of(new SchemeNumber(3), new SchemeNumber(3)));
        var lt = new Application(new SchemeIdentifier("<"), List.of(new SchemeNumber(3), new SchemeNumber(3)));
        var greater = new QuotedExpr(new SchemeString("greater"));
        var less = new QuotedExpr(new SchemeString("less"));

        var clause1 = new SchemeList(List.of(gt, greater));
        var clause2 = new SchemeList(List.of(lt, less));

        var ifForm = new CondClause(List.of(clause1, clause2), new QuotedExpr(new SchemeString("equal")));
        var executionContext = new SchemeContext();
        var actualExpr = ifForm.evaluate(executionContext);
        var expectedExpr = new SchemeString("equal");
        assertEquals(expectedExpr, actualExpr);
    }
}
