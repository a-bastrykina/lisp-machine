package ru.nsu.fit.lispmachine.machine;

import ru.nsu.fit.lispmachine.machine.execution_context.SchemeContext;
import ru.nsu.fit.lispmachine.machine.interpreter.*;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.JavaMethodCall;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {
        var a = new SchemeContext();
        System.out.println("a = " + a);
    }
}
