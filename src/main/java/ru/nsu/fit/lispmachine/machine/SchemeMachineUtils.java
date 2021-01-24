package ru.nsu.fit.lispmachine.machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SchemeMachineUtils {
    private static final BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
    public static String readString() throws IOException {
        return scanner.readLine();
    }

    public static void writeString(String str) {
        System.out.print(str);
    }

    public static void raiseJavaException(String name, String message) throws Throwable {
        var c = Class.forName(name);
        throw (Throwable)c.getDeclaredConstructor(String.class).newInstance(message);
    }

    public static double floor(double val) {
        return Math.floor(val);
    }

    public static String getStdLibrary() {
        return  "(define (display obj) (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"writeString\" obj))" +
                "(define newline (lambda () (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"writeString\" \"\n\")))" +
                "(define read-line (lambda () (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"readString\")))" +
                "(define read read-line)" +
                "(define (throw name message) (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"raiseJavaException\" name message))" +
                "(define (abs x) (if (< x  0) (- x) x))" +
                "(define (map proc items)" +
                "  (if (null? items)" +
                "      '()" +
                "      (cons (proc (car items))" +
                "            (map proc (cdr items)))))";
    }
}
