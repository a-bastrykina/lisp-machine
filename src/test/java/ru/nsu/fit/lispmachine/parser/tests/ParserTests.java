package ru.nsu.fit.lispmachine.parser.tests;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ru.nsu.fit.lispmachine.exceptions.ParseException;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Application;
import ru.nsu.fit.lispmachine.machine.interpreter.Number;
import ru.nsu.fit.lispmachine.machine.interpreter.String;
import ru.nsu.fit.lispmachine.parser.Parser;
import ru.nsu.fit.lispmachine.tokenizer.token.Token;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

public class ParserTests {
	@Test
	public void testParseDecimalNumber() {
		var expected = List.of(new Number(42));
		var parser = new Parser(List.of(new Token(TokenType.NUM10_VALUE, "42"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseBinaryNumber() {
		var expected = List.of(new Number(5));
		var parser = new Parser(List.of(new Token(TokenType.NUM2_VALUE, "#b101"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseOctetNumber() {
		var expected = List.of(new Number(9));
		var parser = new Parser(List.of(new Token(TokenType.NUM8_VALUE, "#b11"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseHexNumber() {
		var expected = List.of(new Number(255));
		var parser = new Parser(List.of(new Token(TokenType.NUM16_VALUE, "#xff"), new Token(TokenType.EOF)).iterator());
		assertEquals(expected, parser.parse());
	}

	@Test
	public void testParseRealNumber() {
		var parser = new Parser(List.of(new Token(TokenType.REAL_VALUE, "1.23"), new Token(TokenType.EOF)).iterator());
		var actual = parser.parse();
		assertTrue(actual.get(0) instanceof Number);
	}

	@Test
	public void testParseApplication() {
		// (+ 5 6)
		var expected = List.of(new Application(new String("+"), List.of(new Number(5), new Number(6)),
				mock(ExecutionContext.class)));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "+"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.NUM10_VALUE, "6"),
				new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF)).iterator());
		var actual = parser.parse();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseNestedApplication() {
		// (+ 5 (* 6 7))
		var expected = List.of(new Application(new String("+"), List.of(new Number(5),
				new Application(new String("*"), List.of(new Number(6), new Number(7)), mock(ExecutionContext.class))),
				mock(ExecutionContext.class)));
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
		var expected = List.of(new Application(new String("+"), List.of(new Number(5), new Number(6)),
				mock(ExecutionContext.class)));
		var parser = new Parser(List.of(new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "+"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.EOF)).iterator());
		parser.parse();
	}
}
