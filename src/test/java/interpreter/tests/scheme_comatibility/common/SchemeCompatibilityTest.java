package interpreter.tests.scheme_comatibility.common;

import org.junit.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;

class TestSchemeMachine {
    private final SchemeContext context = new SchemeContext();

    void simpleTestRun(String prog, String expectedOutput) {
        var replSteps = new Parser(Tokenizer.tokenize(prog)).parse().stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
        assertEquals(expectedOutput, replSteps.get(replSteps.size() - 1).toString());
    }
}

public class SchemeCompatibilityTest {
    private final TestSchemeMachine machine = new TestSchemeMachine();

    @Test
    public void testCalculus() {
        machine.simpleTestRun("(+ 1 (* 2 (+ 3 (* 4 (+ (- 5) 5)))))", "7");
        machine.simpleTestRun("(* 3 4 (- 5 2) 1)", "36");
        machine.simpleTestRun("(* 3 4 (- 5 2) 1 (- 0))", "0");
        machine.simpleTestRun("(* 2)", "2");
        machine.simpleTestRun("(* 0)", "0");
    }

    @Test
    public void testMakeRatDisplay() {
        var prog = "(define (make-rat n d) (cons n d))" +
                "(define (numer x) (car x))" +
                "(define (denom x) (cdr x))" +
                "(define (print-rat x)" +
                "  (display (numer x))" +
                "  (display '/)" +
                "  (display (denom x))" +
                "  (newline))" +
                "(define one-half (make-rat 1 2))" +
                "(print-rat one-half)";
        machine.simpleTestRun(prog, "1/2");
        var prog2 = "(define one-third (make-rat 1 3))" +
                "(print-rat (add-rat one-half one-third))";
        machine.simpleTestRun(prog2, "5/6");
        var prog3 = "(print-rat (mul-rat one-half one-third))";
        machine.simpleTestRun(prog3, "1/6");
        var prog4 = "(print-rat (add-rat one-third one-third))";
        machine.simpleTestRun(prog4, "6/9");

        var gcdProg = "(define (gcd a b)" +
                "  (if (= b 0)" +
                "      a" +
                "      (gcd b (remainder a b))))" +
                "(define (make-rat n d)" +
                "  (let ((g (gcd n d)))" +
                "    (cons (/ n g) (/ d g))))" +
                "(print-rat (add-rat one-third one-third))";
        machine.simpleTestRun(gcdProg, "2/3");
    }

    @Test
    public void baseDefineTest() {
        var prog = "(define a 1)";
        machine.simpleTestRun(prog, "1");
        var prog1 = "(define a (+ 2 2)) \n a";
        machine.simpleTestRun(prog1, "4");
        var prog2 = "(define b (+ 2 a)) \n b";
        machine.simpleTestRun(prog2, "6");
    }

    @Test
    public void carConsCdrNumberListTest() {
        var prog = "(define one-through-four (list 1 2 3 4))" +
                    "one-through-four";
        machine.simpleTestRun(prog, "(1 2 3 4)");
        var prog1 = "(car one-through-four)";
        machine.simpleTestRun(prog1, "1");
        var prog2 = "(cdr one-through-four)";
        machine.simpleTestRun(prog2, "( 2 3 4)");
        var prog3 = "(car (cdr one-through-four))";
        machine.simpleTestRun(prog3, "2");
        var prog4 = "(cons 10 one-through-four)";
        machine.simpleTestRun(prog4, "(10 1 2 3 4)");
        var prog5 = "(cons 5 one-through-four)";
        machine.simpleTestRun(prog5, "(5 1 2 3 4");
    }

}