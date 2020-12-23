package ru.nsu.fit.lispmachine.tokenizer.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

public class TokenTypeTests {

	@Test
	public void testRecognizeIdentifier() {
		Assert.assertEquals(TokenType.IDENTIFIER, TokenType.recognizeType("num-ber?"));
	}

	@Test
	public void testRecognizeNum10() {
		Assert.assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("3"));
		Assert.assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("-3"));
		Assert.assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("+3"));
		Assert.assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("342"));
		Assert.assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("-342"));
		Assert.assertEquals(TokenType.NUM10_VALUE, TokenType.recognizeType("+342"));
	}

	@Test
	public void testRecognizeNum2() {
		Assert.assertEquals(TokenType.NUM2_VALUE, TokenType.recognizeType("#b0"));
		Assert.assertEquals(TokenType.NUM2_VALUE, TokenType.recognizeType("#b1"));
		Assert.assertEquals(TokenType.NUM2_VALUE, TokenType.recognizeType("#b010"));
	}

	@Test
	public void testRecognizeNum8() {
		Assert.assertEquals(TokenType.NUM8_VALUE, TokenType.recognizeType("#o7"));
		Assert.assertEquals(TokenType.NUM8_VALUE, TokenType.recognizeType("#o01234567"));
	}

	@Test
	public void testRecognizeNum16() {
		Assert.assertEquals(TokenType.NUM16_VALUE, TokenType.recognizeType("#xf"));
		Assert.assertEquals(TokenType.NUM16_VALUE, TokenType.recognizeType("#x0123456789abcdef"));
	}

	@Test
	public void testRecognizeChar() {
		Assert.assertEquals(TokenType.CHARACTER_VALUE, TokenType.recognizeType("#\\a"));
		Assert.assertEquals(TokenType.CHARACTER_VALUE, TokenType.recognizeType("#\\newline"));
		Assert.assertEquals(TokenType.CHARACTER_VALUE, TokenType.recognizeType("#\\space"));
	}

	@Test
	public void testRecognizeBoolean() {
		Assert.assertEquals(TokenType.BOOLEAN_VALUE, TokenType.recognizeType("#t"));
		Assert.assertEquals(TokenType.BOOLEAN_VALUE, TokenType.recognizeType("#f"));
		Assert.assertEquals(TokenType.BOOLEAN_VALUE, TokenType.recognizeType("#T"));
		Assert.assertEquals(TokenType.BOOLEAN_VALUE, TokenType.recognizeType("#F"));
	}

	@Test
	public void testRecognizeString() {
		Assert.assertEquals(TokenType.STRING_VALUE, TokenType.recognizeType("\"Hello\\\"\n\tworld\""));
	}

	@Test
	public void testRecognizeReal() {
		Assert.assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("11e-10"));
		Assert.assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType(".11e+10"));
		Assert.assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType(".11"));
		Assert.assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("11.110e+21"));
		Assert.assertEquals(TokenType.REAL_VALUE, TokenType.recognizeType("11.110"));
	}
}
