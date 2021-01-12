package interpreter.tests.scheme_comatibility.common;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        //todo it's parser fail
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
        var prog3 = "(define (plus a b) (+ a b)) \n (plus 1 1)";
        machine.simpleTestRun(prog3, "2");

    }

    @Test
    public void carConsCdrNumberListTest() {
        var prog = "(define one-through-four (list 1 2 3 4))" +
                "one-through-four";
        machine.simpleTestRun(prog, "(1 2 3 4)");
        var prog1 = "(car one-through-four)";
        machine.simpleTestRun(prog1, "1");
        var prog2 = "(cdr one-through-four)";
        machine.simpleTestRun(prog2, "(2 3 4)");
        var prog3 = "(car (cdr one-through-four))";
        machine.simpleTestRun(prog3, "2");
        var prog4 = "(cons 10 one-through-four)";
        machine.simpleTestRun(prog4, "(10 1 2 3 4)");
        var prog5 = "(cons 5 one-through-four)";
        machine.simpleTestRun(prog5, "(5 1 2 3 4)");
    }

    @Test
    public void consAsPairs() {
        var prog = "(define lst1 '(1 2 3))\n" +
                "(define lst2 '(4 5))\n" +
                "\n" +
                "(cons (car lst1) (car lst2))";
        machine.simpleTestRun(prog, "(1 . 4)");
        var prog1 =
                "(list (cons (car lst1) (car lst2))\n" +
                        "      (cons (car (cdr lst1)) (car (cdr lst2))))";
        machine.simpleTestRun(prog1, "((1 . 4) (2 . 5))");
        var prog2 = "(cons 2 '())";
        machine.simpleTestRun(prog2, "(2)");
        var prog3 = "(cons 3 (cons 2 '()))";
        machine.simpleTestRun(prog3, "(3 2)");

        //todo: it's parser fail
        var prog4 = "(cons (cons 3 (cons 2 '())) 'bla)";
        machine.simpleTestRun(prog4, "((3 2) . bla)");
    }

    @Test
    public void mapTest() {
        //todo: parser fail
        //(define nil '())
        // todo it's parser fail
        // (map abs (list -10 2.5 -11.6 17))\n;

        //todo move map and abs to standard library
        var prog =
                "(define (abs x) (if (< x  0) (- x) x))" +
                        "(define (map proc items)" +
                        "  (if (null? items)" +
                        "      '()" +
                        "      (cons (proc (car items))" +
                        "            (map proc (cdr items)))))" +
                        "(map abs (list -10 2.5 -11 17))";
        machine.simpleTestRun(prog, "(10 2.5 11 17)");

        var prog1 = "(map (lambda (x) (* x x))\n" +
                "     (list 1 2 3 4))\n";
        machine.simpleTestRun(prog1, "(1 4 9 16)");

        var prog2 = "(define (scale-list items factor)\n" +
                "  (map (lambda (x) (* x factor))\n" +
                "       items))\n" +
                "(scale-list (list 1 2 3 4 5) 10)\n";
        machine.simpleTestRun(prog2, "(10 20 30 40 50)");
    }

    @Test
    public void lenTest() {
        var prog1 = "(define (len s)\n" +
                "  (if (null? s)\n" +
                "    0\n" +
                "    (+ 1 (len (cdr s))))) \n (len '(1 2 3 4 5))";
        machine.simpleTestRun(prog1, "5");
    }

    @Test
    public void peterNorvigTest() {
        var prog1 = "(define double (lambda (z) (* 2 z))) \n (double 5)";
        machine.simpleTestRun(prog1, "10");

        var prog2 = "(define compose (lambda (f g) (lambda (x) (f (g x)))))"
                + "\n ((compose list double) 5)";
        machine.simpleTestRun(prog2, "(10)");

        var prog3 = "(define apply-twice (lambda (f) (compose f f)))\n ((apply-twice double) 5)";
        machine.simpleTestRun(prog3, "20");

        var prog4 = "((apply-twice (apply-twice double)) 5)";
        machine.simpleTestRun(prog4, "80");
//
        var factorial = "(define fact (lambda (n) (if (< n 2) 1 (* n (fact (- n 1)))))) \n (fact 3)";
        machine.simpleTestRun(factorial, "6");
//
        //todo: long math
//        machine.simpleTestRun("(fact 50)", "30414093201713378043612608166064768844377641568960512000000000000");

        var prog5 = "(define (combine f)\n" +
                "  (lambda (x y)\n" +
                "    (if (null? x) '()\n" +
                "      (f (list (car x) (car y))\n" +
                "         ((combine f) (cdr x) (cdr y))))))\n" +
                "(define zip (combine cons))\n" +
                "(zip (list 1 2 3 4) (list 5 6 7 8))";
        machine.simpleTestRun(prog5, "((1 5) (2 6) (3 7) (4 8))");

    }

    @Test
    public void calcWithNotNumbersTest() {
        var prog = "(define (f x) (* 2 x))\n (f 3)";
        machine.simpleTestRun(prog, "6");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> machine.simpleTestRun("(f '())","6"));
        assertEquals("* called with non numbers arguments", exception.getMessage());
    }
}
