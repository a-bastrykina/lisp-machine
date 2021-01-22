package ru.nsu.fit.lispmachine.machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SchemeMachineUtils {
    public static String readString() throws IOException {
        var scanner = new BufferedReader(new InputStreamReader(System.in));
        return scanner.readLine();
    }

    public static void writeString(String str) throws IOException {
        System.out.print(str);
    }
}
