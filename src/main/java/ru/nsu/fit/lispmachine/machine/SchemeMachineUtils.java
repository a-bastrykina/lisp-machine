package ru.nsu.fit.lispmachine.machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SchemeMachineUtils {
	private static final BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Read line from the standart input
	 * Called using java-call special form
	 *
	 * @return
	 * @throws IOException
	 */
	public static String readString() throws IOException {
		return scanner.readLine();
	}

	/**
	 * Write line into the standart output.
	 * Called using java-call special form
	 */
	public static void writeString(String str) {
		System.out.print(str);
	}

	/**
	 * Raise java exception.
	 * Called using java-call special form
	 *
	 * @param name exception name
	 * @param message exception message
	 * @throws Throwable
	 */
	public static void raiseJavaException(String name, String message) throws Throwable {
		var c = Class.forName(name);
		throw (Throwable) c.getDeclaredConstructor(String.class).newInstance(message);
	}

	/**
	 * Get Scheme standart library
	 *
	 * @return standart library written in Scheme
	 */
	public static String getStdLibrary() {
		return "(define (display obj) (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"writeString\" obj))"
				+
				"(define newline (lambda () (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"writeString\" \"\n\")))"
				+
				"(define read-line (lambda () (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"readString\")))"
				+
				"(define (throw name message) (java-call \"ru.nsu.fit.lispmachine.machine.SchemeMachineUtils\" \"raiseJavaException\" name message))"
				+
				"(define (abs x) (if (< x  0) (- x) x))" +
				"(define (map proc items)" +
				"  (if (null? items)" +
				"      '()" +
				"      (cons (proc (car items))" +
				"            (map proc (cdr items)))))";
	}
}
