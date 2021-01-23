package ru.nsu.fit.lispmachine.tokenizer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.function.Predicate;

import ru.nsu.fit.lispmachine.exceptions.TokenizeException;
import ru.nsu.fit.lispmachine.tokenizer.token.Token;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

public class Tokenizer {

	public static TokenIterator tokenize(File file) {
		try {
			return new TokenIterator(new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static TokenIterator tokenize(String inputString) {
		try {
			return new TokenIterator(
					new BufferedReader(new InputStreamReader(new ByteArrayInputStream(inputString.getBytes()))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static class TokenIterator implements Iterator<Token> {
		final BufferedReader r;
		boolean eofReached = false;
		int currentChar;

		private static final int EOF = -1;
		private static final int START_STRING_CHAR = '\"';
		private static final int SHARP_CHAR = '#';
		private static final int OPEN_BRACE_CHAR = '(';

		TokenIterator(BufferedReader r) throws IOException {
			this.r = r;
			currentChar = r.read();
		}

		@Override public boolean hasNext() {
			return !eofReached;
		}

		@Override public Token next() {
			try {
				if (currentChar == EOF) {
					eofReached = true;
					return new Token(TokenType.EOF);
				}

				// Skip whitespaces
				while (Character.isWhitespace(currentChar)) {
					currentChar = r.read();
				}

				if (currentChar == EOF) {
					eofReached = true;
					return new Token(TokenType.EOF);
				}

				StringBuilder dataBuilder = new StringBuilder();

				// Try match single character
				TokenType type = TokenType.matchCharacter(currentChar);
				if (type != null) {
					currentChar = r.read();
					return new Token(type);
				}

				if (currentChar == START_STRING_CHAR) {
					currentChar = r.read();
					dataBuilder.append(Character.toString(START_STRING_CHAR));
					readString(dataBuilder, (ch) -> ch != '\"', true);
				} else {
					if (currentChar == SHARP_CHAR) {
						dataBuilder.append(Character.toString(SHARP_CHAR));
						currentChar = r.read();
						if (currentChar == OPEN_BRACE_CHAR) {
							currentChar = r.read();
							return new Token(TokenType.VECTOR_START);
						}
					}
					readString(dataBuilder, (ch) -> !Character.isWhitespace(ch) && ch != ')', false);
				}

				String data = dataBuilder.toString();
				return new Token(TokenType.recognizeType(data), data);
			} catch (IOException e) {
				throw new RuntimeException(e);
				throw new TokenizeException(e);
			}
		}

		private void readString(StringBuilder sb, Predicate<Integer> continueCriteria, boolean appendLast)
				throws IOException {
			while (continueCriteria.test(currentChar) && currentChar != EOF) {
				sb.append(Character.toString(currentChar));
				currentChar = r.read();
			}
			if (appendLast && currentChar != EOF) {
				sb.append(Character.toString(currentChar));
				currentChar = r.read();
			}
		}

	}
}
