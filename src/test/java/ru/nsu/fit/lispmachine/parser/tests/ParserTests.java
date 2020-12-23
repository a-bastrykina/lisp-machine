package ru.nsu.fit.lispmachine.parser.tests;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;
import ru.nsu.fit.lispmachine.exceptions.ParseException;
import ru.nsu.fit.lispmachine.machine.interpreter.Application;
import ru.nsu.fit.lispmachine.machine.interpreter.Define;
import ru.nsu.fit.lispmachine.machine.interpreter.IfClause;
import ru.nsu.fit.lispmachine.machine.interpreter.Lambda;
import ru.nsu.fit.lispmachine.machine.interpreter.QuotedExpr;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeBool;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemerString;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.token.Token;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

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
	public void testParseBoolean() {
		var expected = List.of(new SchemeBool(true));
		var parser = new Parser(List.of(new Token(TokenType.BOOLEAN_VALUE, "#t"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseApplication() {
		// (+ 5 6)
		var expected = List
				.of(new Application(new SchemerString("+"), List.of(new SchemeNumber(5), new SchemeNumber(6))));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "+"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.NUM10_VALUE, "6"),
				new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF)).iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseNestedApplication() {
		// (+ 5 (* 6 7))
		var expected = List.of(new Application(new SchemerString("+"), List.of(new SchemeNumber(5),
				new Application(new SchemerString("*"), List.of(new SchemeNumber(6), new SchemeNumber(7))))));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "+"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.OPEN_BRACE),
				new Token(TokenType.IDENTIFIER, "*"), new Token(TokenType.NUM10_VALUE, "6"),
				new Token(TokenType.NUM10_VALUE, "7"),
				new Token(TokenType.CLOSE_BRACE), new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF))
				.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test(expected = ParseException.class)
	public void testParseApplication_Fail() {
		// (+ 5
		var expected = List
				.of(new Application(new SchemerString("+"), List.of(new SchemeNumber(5), new SchemeNumber(6))));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "+"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.EOF)).iterator());
		parser.parse();
	}

	@Test
	public void testParseLambda() {
		// (lambda (r) (* r r))
		var expected = List
				.of(new Lambda(List.of(new SchemerString("r")),
						new Application(new SchemerString("*"), List.of(new SchemerString("r"),
								new SchemerString("r")))));
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
		// (define (square r) (* r r))
		var expected = List
				.of(new Define(new SchemerString("square"), List.of(new SchemerString("r")),
						new Application(new SchemerString("*"), List.of(new SchemerString("r"),
								new SchemerString("r")))));
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
				.of(new QuotedExpr(new SchemerString("a")));
		var parser = new Parser(
				List.of(new Token(TokenType.QUOTE), new Token(TokenType.IDENTIFIER, "a"), new Token(TokenType.EOF))
						.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseQuoteApplication() {
		// (quote a)
		var expected = List
				.of(new QuotedExpr(new SchemerString("a")));
		var parser = new Parser(
				List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "quote"),
						new Token(TokenType.IDENTIFIER, "a"), new Token(TokenType.CLOSE_BRACE),
						new Token(TokenType.EOF))
						.iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}
}
