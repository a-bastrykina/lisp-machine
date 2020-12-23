package ru.nsu.fit.lispmachine.tokenizer.tests;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.lispmachine.tokenizer.Tokenizer;
import ru.nsu.fit.lispmachine.tokenizer.token.Token;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

public class TokenizerTests {

	private List<Token> collectTokens(Iterator<Token> iter) {
		var splitr = Spliterators.spliteratorUnknownSize(iter, Spliterator.NONNULL);
		return StreamSupport.stream(splitr, false).collect(Collectors.toList());
	}

	private void test(String input, Token... expectedTokens) {
		List<Token> actual = collectTokens(Tokenizer.tokenize(input));
		Assert.assertEquals(Arrays.asList(expectedTokens), actual);
	}

	@Test
	public void testTokenizePredicate() {
		test("(number? 5)", new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "number?"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF));
	}

	@Test
	public void testTokenizePredicate_WithWhitespace() {
		test("  ( number? 5 )  ", new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "number?"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF));
	}

	@Test
	public void testTokenizePredicate_WithTabAndNewline() {
		test("\n(\n\tnumber?\n\t\t5\n)", new Token(TokenType.OPEN_BRACE, null),
				new Token(TokenType.IDENTIFIER, "number?"),
				new Token(TokenType.NUM10_VALUE, "5"), new Token(TokenType.CLOSE_BRACE), new Token(TokenType.EOF));
	}

	@Test
	public void testTokenizeHelloWorld() {
		test("(begin\n"
						+ "\t(display \"Hello, World!\")\n"
						+ "\t(newline))",
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "begin"),
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "display"),
				new Token(TokenType.STRING_VALUE, "\"Hello, World!\""), new Token(TokenType.CLOSE_BRACE, null),
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "newline"),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.EOF)
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTokenizeUnexpectedEOF() {
		var iterator = Tokenizer.tokenize("(display \"Hello, W");
		Assert.assertEquals(new Token(TokenType.OPEN_BRACE), iterator.next());
		Assert.assertEquals(new Token(TokenType.IDENTIFIER, "display"), iterator.next());
		iterator.next();
	}

	@Test
	public void testTokenizeAbbreviation() {
		test("(sum-list '(6 8 100))",
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "sum-list"),
				new Token(TokenType.ABBREVIATION), new Token(TokenType.OPEN_BRACE),
				new Token(TokenType.NUM10_VALUE, "6"), new Token(TokenType.NUM10_VALUE, "8"),
				new Token(TokenType.NUM10_VALUE, "100"),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.EOF)
		);
	}

	@Test
	public void testTokenizeVector() {
		test("(append #(1 2) 42)",
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "append"),
				new Token(TokenType.VECTOR_START),
				new Token(TokenType.NUM10_VALUE, "1"), new Token(TokenType.NUM10_VALUE, "2"),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.NUM10_VALUE, "42"),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.EOF)
		);
	}

	@Test
	public void testTokenizeBoolean() {
		test("(or #t #f)",
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "or"),
				new Token(TokenType.BOOLEAN_VALUE, "#t"), new Token(TokenType.BOOLEAN_VALUE, "#f"),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.EOF)
		);
	}

	@Test
	public void testTokenizeReal() {
		test("(+ 1.2 3.4e-10)",
				new Token(TokenType.OPEN_BRACE), new Token(TokenType.IDENTIFIER, "+"),
				new Token(TokenType.REAL_VALUE, "1.2"), new Token(TokenType.REAL_VALUE, "3.4e-10"),
				new Token(TokenType.CLOSE_BRACE),
				new Token(TokenType.EOF)
		);
	}
}
