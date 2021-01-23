package ru.nsu.fit.lispmachine.interpreter.scheme_comatibility.common;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class HomeMadeTests {
    private final TestSchemeMachine machine = new TestSchemeMachine();

    @Test
    public void testCalculusWithLambda() {
        machine.simpleTestRun("((lambda (x) (+ 5 (* x x))) (+ 123 (* 5 (- 10 5) )) ) ", "21909");
    }

    @Test
    public void testArea() {
        machine.simpleTestRun("(define (area r) (* r r) ) \n (area 15)", "225");
    }

    @Test
    public void testFibonacci() {
        machine.simpleTestRun("(define (fib x) (if (< x 2) 1 (+ (fib (- x 1)) (fib (- x 2)))))\n (fib 10)", "89");
    }

    @Test
    public void testListDifferentTypes() {
        machine.simpleTestRun("(list 1 2 3 +)", "(1 2 3 #<Function +>)");
    }

    @Test
    public void testCountElemInList() {
        var prog = "(define (toint r) (if r 1 0 ))"
                + "(define (count item l) (if l (+ (toint (= item (car l))) (count item (cdr l))) 0))"
                + "(count 0 (list 0 1 2 3 0 0))";
        machine.simpleTestRun(prog, "3");
    }

    @Test
    public void testSetWorks() {
        var setPart = "(set! r2 (lambda (r) (* r r)))";
        var exp = assertThrows(IllegalArgumentException.class, () -> machine.simpleTestRun(setPart, ""));
        assertEquals(exp.getMessage(), "Definition with name r2 not found");
        var prog = "(define r2 10) \n r2";
        machine.simpleTestRun(prog, "10");
        machine.simpleTestRun(setPart + "(r2 10)", "100");
    }

    @Test
    public void javaStaticMethodCall() {
        var prog = "(java-call \"java.lang.Double\" \"sum\" 1 2)";
        machine.simpleTestRun(prog, "3.0");
    }

    @Test
    public void notLazyExecutionTest() {
        var prog = "(define (try a b) " +
                            "(if (= a 0) 1 b)) \n (try 0 (/ 1 0))";
        var exception = assertThrows(ArithmeticException.class,
                () -> machine.simpleTestRun(prog, "1"));
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    public void lazyExecutionTest() {
        var prog = "(define (try a b) " +
                "(if (= a 0) 1 b)) \n (try 0 (/ 1 0))";
        machine.simpleTestRun(prog, "1");
    }
}
