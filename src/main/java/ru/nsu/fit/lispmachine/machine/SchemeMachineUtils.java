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
        System.out.println(str);
    }

    public static String getStdLibrary() {
        return  "(define (display obj) (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"writeString\" obj))" +
                "(define read-line (lambda () (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"readString\")))\n";
    }
}
