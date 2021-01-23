package ru.nsu.fit.lispmachine.tokenizer;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTypeTests {

	@Test
	public void testRecognizeIdentifier() {
		assertEquals(TokenType.IDENTIFIER, TokenType.recognizeType("num-ber?"));
		assertEquals(TokenType.IDENTIFIER, TokenType.recognizeType("r2"));
	}

	@Test
	public void testRecognizeNum10() {
		assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("3"));
		assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("-3"));
		assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("+3"));
		assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("342"));
		assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("-342"));
		assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("+342"));
	}

	@Test
	public void testRecognizeNum2() {
		assertEquals(TokenType.NUM2_VALUE, TokenType.recognizeType("#b0"));
		assertEquals(TokenType.NUM2_VALUE, TokenType.recognizeType("#b1"));
		assertEquals(TokenType.NUM2_VALUE, TokenType.recognizeType("#b010"));
	}

	@Test
	public void testRecognizeNum8() {
		assertEquals(TokenType.NUM8_VALUE, TokenType.recognizeType("#o7"));
		assertEquals(TokenType.NUM8_VALUE, TokenType.recognizeType("#o01234567"));
	}

	@Test
	public void testRecognizeNum16() {
		assertEquals(TokenType.NUM16_VALUE, TokenType.recognizeType("#xf"));
		assertEquals(TokenType.NUM16_VALUE, TokenType.recognizeType("#x0123456789abcdef"));
	}

	@Test
	public void testRecognizeChar() {
		assertEquals(TokenType.CHARACTER_VALUE, TokenType.recognizeType("#\\a"));
		assertEquals(TokenType.CHARACTER_VALUE, TokenType.recognizeType("#\\newline"));
		assertEquals(TokenType.CHARACTER_VALUE, TokenType.recognizeType("#\\space"));
	}

	@Test
	public void testRecognizeBoolean() {
		assertEquals(TokenType.BOOLEAN_VALUE, TokenType.recognizeType("#t"));
		assertEquals(TokenType.BOOLEAN_VALUE, TokenType.recognizeType("#f"));
		assertEquals(TokenType.BOOLEAN_VALUE, TokenType.recognizeType("#T"));
		assertEquals(TokenType.BOOLEAN_VALUE, TokenType.recognizeType("#F"));
	}

	@Test
	public void testRecognizeString() {
		assertEquals(TokenType.STRING_VALUE, TokenType.recognizeType("\"Hello\\\"\n\tworld\""));
	}

	@Test
	public void testRecognizeReal() {
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("11e-10"));
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("-11e-10"));
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType(".11e+10"));
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("+.11e+10"));
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType(".11"));
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("-.11"));
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("11.110e+21"));
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("+11.110e+21"));
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("11.110"));
		assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("-11.110"));
	}
}
