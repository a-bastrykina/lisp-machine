package ru.nsu.fit.lispmachine.machine;

import ru.nsu.fit.lispmachine.exceptions.CompatibilityException;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;

import java.io.IOException;
import java.util.stream.Collectors;

public class SchemeMachine {
    private final ExecutionContext context = new SchemeContext();
    private long lineNumber = 0;

    SchemeMachine() {
        var exprs = new Parser(Tokenizer.tokenize(SchemeMachineUtils.getStdLibrary())).parse();
        for (Expression expr : exprs) {
            try {
                expr.evaluate(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Expression execLine(String line) {
        if (line.strip().isEmpty())
            return null;
        lineNumber++;
        var exprs = new Parser(Tokenizer.tokenize(line)).parse();
        exprs = exprs.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
        return exprs.get(exprs.size() - 1);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    void replMode() {
        while (true) {
            try {
                System.err.println("> ");
                String st = SchemeMachineUtils.readString();
                var res = execLine(st);
                if (res != null)
                    System.out.println(res);
            } catch (IOException ignored) {
            } catch (CompatibilityException exc) {
                System.out.println("Exception " + exc.getRootCase() + " were raised: " + " ; line:" + lineNumber);
            } catch (Exception m) {
                System.out.println("Error: " + m.getMessage() + " ; line:" + lineNumber);
            }
        }
    }

    public static void main(String[] args) {
        var m = new SchemeMachine();
        m.replMode();
    }

}

