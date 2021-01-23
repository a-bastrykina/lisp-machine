package ru.nsu.fit.lispmachine.machine;

import ru.nsu.fit.lispmachine.exceptions.CompatibilityException;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * A class to represent Scheme interpreter.
 */
public class SchemeMachine {
    private final ExecutionContext context;
    private long lineNumber = 0;

    /**
     * @param enableLaziness whether to enable lazy computations.
     */
    public SchemeMachine(boolean enableLaziness) {
        var exprs = new Parser(Tokenizer.tokenize(SchemeMachineUtils.getStdLibrary())).parse();
        context = new SchemeContext(enableLaziness);
        for (Expression expr : exprs) {
            try {
                expr.evaluate(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Transform a single-line string into an
     * expression of the Scheme Object model.
     * @param line input string
     * @return
     */
    public Expression execLine(String line) {
        if (line.strip().isEmpty())
            return null;
        lineNumber++;
        var exprs = new Parser(Tokenizer.tokenize(line)).parse();
        exprs = exprs.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
        return exprs.get(exprs.size() - 1);
    }

    /**
     * Interpret expressinons in an infinite loop.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void replMode() {
        while (true) {
            try {
                System.err.println("> ");
                String st = SchemeMachineUtils.readString();
                var res = execLine(st);
                if (res != null)
                    System.out.println(context.getActualExpressionValue(res));
            } catch (IOException ignored) {
            } catch (CompatibilityException exc) {
                System.out.println("Exception " + exc.getRootCase() + " were raised: " + " ; line:" + lineNumber);
            } catch (Exception m) {
                System.out.println("Error: " + m.getMessage() + " ; line:" + lineNumber);
            }
        }
    }

    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        SchemeMachine m;
        if (args.length < 1 || !args[0].equals("--lazy")) {
            m = new SchemeMachine(false);
        } else {
            m = new SchemeMachine(true);
        }
        m.replMode();
    }
}

