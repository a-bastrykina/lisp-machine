package ru.nsu.fit.lispmachine.parser;

import java.util.List;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.exceptions.ParseException;
import ru.nsu.fit.lispmachine.machine.interpreter.Application;
import ru.nsu.fit.lispmachine.machine.interpreter.Assignment;
import ru.nsu.fit.lispmachine.machine.interpreter.Begin;
import ru.nsu.fit.lispmachine.machine.interpreter.Define;
import ru.nsu.fit.lispmachine.machine.interpreter.IfClause;
import ru.nsu.fit.lispmachine.machine.interpreter.Lambda;
import ru.nsu.fit.lispmachine.machine.interpreter.QuotedExpr;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeBool;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeChar;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeString;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeIdentifier;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.JavaMethodCall;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.token.Token;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {

	@Test
	public void testParseDecimalNumber() {
		var expected = List.of(new SchemeNumber(42));
		var parser = new Parser(List.of(new Token(TokenType.NUM10_VALUE, "42"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseBinaryNumber() {
		var expected = List.of(new SchemeNumber(5));
		var parser = new Parser(List.of(new Token(TokenType.NUM2_VALUE, "#b101"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseOctetNumber() {
		var expected = List.of(new SchemeNumber(9));
		var parser = new Parser(List.of(new Token(TokenType.NUM8_VALUE, "#b11"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseHexNumber() {
		var expected = List.of(new SchemeNumber(255));
		var parser = new Parser(List.of(new Token(TokenType.NUM16_VALUE, "#xff"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseRealNumber() {
		var parser = new Parser(List.of(new Token(TokenType.REAL_VALUE, "1.23"), new Token(TokenType.EOF)).iterator());
		var actual = parser.parse();
		assertTrue(actual.get(0) instanceof SchemeNumber);
		assertEquals(1.23, ((SchemeNumber) actual.get(0)).getValue().doubleValue(), 0.001);
	}

	@Test
	public void testParseCharacter() {
		var expected = List.of(new SchemeChar('a'));
		var parser = new Parser(
				List.of(new Token(TokenType.CHARACTER_VALUE, "#\\a"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseNewlineCharacter() {
		var expected = List.of(new SchemeChar('\n'));
		var parser = new Parser(
				List.of(new Token(TokenType.CHARACTER_VALUE, "#\\newline"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseSpaceCharacter() {
		var expected = List.of(new SchemeChar(' '));
		var parser = new Parser(
				List.of(new Token(TokenType.CHARACTER_VALUE, "#\\space"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseBoolean() {
		var expected = List.of(new SchemeBool(true));
		var parser = new Parser(List.of(new Token(TokenType.BOOLEAN_VALUE, "#t"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseString() {
		var expected = List.of(new SchemeString("Hello, world!\n"));
		var parser = new Parser(
				List.of(new Token(TokenType.STRING_VALUE, "\"Hello, world!\n\""), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}


	@Test
	public void testParseApplication() {
		// (+ 5 6)
		var expected = List
				.of(new Application(new SchemeIdentifier("+"), List.of(new SchemeNumber(5), new SchemeNumber(6))));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "+"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.NUM10_VALUE, "6"),
				new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF)).iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseNestedApplication() {
		// (+ 5 (* 6 7))
		var expected = List.of(new Application(new SchemeIdentifier("+"), List.of(new SchemeNumber(5),
				new Application(new SchemeIdentifier("*"), List.of(new SchemeNumber(6), new SchemeNumber(7))))));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "+"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.OPEN_BRACE),
				new Token(TokenType.IDENTIFIER, "*"), new Token(TokenType.NUM10_VALUE, "6"),
				new Token(TokenType.NUM10_VALUE, "7"),
				new Token(TokenType.CLOSE_BRACE), new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF))
				.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test()
	public void testParseApplication_Fail() {
		// (+ 5
		var expected = List
				.of(new Application(new SchemeIdentifier("+"), List.of(new SchemeNumber(5), new SchemeNumber(6))));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "+"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.EOF)).iterator());
		assertThrows(ParseException.class, parser::parse);
	}

	@Test
	public void testParseLambda() {
		// (lambda (r) (* r r))
		var expected = List
				.of(new Lambda(List.of(new SchemeIdentifier("r")),
						new Application(new SchemeIdentifier("*"), List.of(new SchemeIdentifier("r"),
								new SchemeIdentifier("r")))));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "lambda"),
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "r"), new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "*"),
				new Token(TokenType.IDENTIFIER, "r"), new Token(TokenType.IDENTIFIER, "r"),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF)).iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseDefine() {
		// (define my-name "Alena")
		var expected = List
				.of(new Define(new SchemeIdentifier("my-name"), new SchemeString("Alena")));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "define"),
				new Token(TokenType.IDENTIFIER, "my-name"),
				new Token(TokenType.STRING_VALUE, "\"Alena\""),
				new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF)).iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

    @Test
    public void testParseDefineQuoted() {
        // (define numbers '(1 2))
        var expected = List
                .of(new Define(new SchemeIdentifier("numbers"),
                        new QuotedExpr(new SchemeList(List.of(new SchemeNumber(1), new SchemeNumber(2))))));

        var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "define"),
                new Token(TokenType.IDENTIFIER, "numbers"),
                new Token(TokenType.QUOTE),
                new Token(TokenType.OPEN_BRACE),
                new Token(TokenType.NUM10_VALUE, "1"),
                new Token(TokenType.NUM10_VALUE, "2"),
                new Token(TokenType.CLOSE_BRACE),
                new Token(TokenType.CLOSE_BRACE)).iterator());
        var actual = parser.parse();
        assertEquals(expected, actual);
    }

	@Test
	public void testParseDefineWithParams() {
		// (define (square r) (* r r))
		var expected = List
				.of(new Define(new SchemeIdentifier("square"), List.of(new SchemeIdentifier("r")),
						new Application(new SchemeIdentifier("*"), List.of(new SchemeIdentifier("r"),
								new SchemeIdentifier("r")))));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "define"),
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "square"),
				new Token(TokenType.IDENTIFIER, "r"), new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "*"),
				new Token(TokenType.IDENTIFIER, "r"), new Token(TokenType.IDENTIFIER, "r"),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF)).iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseIf() {
		// (if #t 1 2)
		var expected = List
				.of(new IfClause(new SchemeBool(true), new SchemeNumber(1), new SchemeNumber(2)));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "if"),
				new Token(TokenType.BOOLEAN_VALUE, "#t"), new Token(TokenType.NUM10_VALUE, "1"),
				new Token(TokenType.NUM10_VALUE, "2"), new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF))
				.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseQuote() {
		// 'a
		var expected = List
				.of(new QuotedExpr(new SchemeIdentifier("a")));
		var parser = new Parser(
				List.of(new Token(TokenType.QUOTE), new Token(TokenType.IDENTIFIER, "a"), new Token(TokenType.EOF))
						.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseQuoteList() {
		// '(1 2)
		var expected = List
				.of(new QuotedExpr(new SchemeList(List.of(new SchemeNumber(1), new SchemeNumber(2)))));
		var parser = new Parser(
				List.of(new Token(TokenType.QUOTE), new Token(TokenType.OPEN_BRACE),
						new Token(TokenType.NUM10_VALUE, "1"), new Token(TokenType.NUM10_VALUE, "2"),
						new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF))
						.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseQuoteApplication() {
		// (quote a)
		var expected = List
				.of(new QuotedExpr(new SchemeIdentifier("a")));
		var parser = new Parser(
				List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "quote"),
						new Token(TokenType.IDENTIFIER, "a"), new Token(TokenType.CLOSE_BRACE),
						new Token(TokenType.EOF))
						.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseAssignment() {
		// (set! my-var 42)
		var expected = List
				.of(new Assignment(new SchemeIdentifier("my-var"), new SchemeNumber(42)));
		var parser = new Parser(
				List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "set!"),
						new Token(TokenType.IDENTIFIER, "my-var"), new Token(TokenType.NUM10_VALUE, "42"),
						new Token(TokenType.CLOSE_BRACE),
						new Token(TokenType.EOF))
						.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseBegin() {
		// (begin 1 2 3)
		var expected = List
				.of(new Begin(List.of(new SchemeNumber(1), new SchemeNumber(2), new SchemeNumber(3))));
		var parser = new Parser(
				List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "begin"),
						new Token(TokenType.NUM10_VALUE, "1"), new Token(TokenType.NUM10_VALUE, "2"),
						new Token(TokenType.NUM10_VALUE, "3"),
						new Token(TokenType.CLOSE_BRACE),
						new Token(TokenType.EOF))
						.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseJavaCall() {
		// (java-call "Double" "sum" 5 10)
		var expected = List
				.of(new JavaMethodCall(new SchemeString("Double"), new SchemeString("sum"),
						List.of(new SchemeNumber(5), new SchemeNumber(10))));
		var parser = new Parser(
				List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "java-call"),
						new Token(TokenType.STRING_VALUE, "\"Double\""), new Token(TokenType.STRING_VALUE, "\"sum\""),
						new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.NUM10_VALUE, "10"),
						new Token(TokenType.CLOSE_BRACE),
						new Token(TokenType.EOF))
						.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}
}
