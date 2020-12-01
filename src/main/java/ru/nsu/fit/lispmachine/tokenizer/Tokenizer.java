package ru.nsu.fit.lispmachine.tokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Tokenizer {

	public static Stream<Token> tokenize(File file) throws FileNotFoundException {
		return createStream(new Scanner(file));
	}

	public static Stream<Token> tokenize(String inputString) {
		return createStream(new Scanner(inputString));
	}

	private static Stream<Token> createStream(Scanner sc) {
		var splitr = Spliterators.spliteratorUnknownSize(new TokenIterator(sc), Spliterator.NONNULL);
		return StreamSupport.stream(splitr, false).onClose(sc::close);
	}

	private static class TokenIterator implements Iterator<Token> {
		final Scanner sc;

		TokenIterator(Scanner sc) {
			this.sc = sc;
		}

		@Override public boolean hasNext() {
			return sc.hasNext();
		}

		@Override public Token next() {
			if (sc.hasNext()) {
				var data = sc.next();
				var tokenType = TokenType.recognizeType(data);
				return new Token(tokenType, data);
			} else {
				throw new IllegalStateException("No tokens are available");
			}
		}
	}
}
