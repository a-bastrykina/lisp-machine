package ru.nsu.fit.lispmachine.tokenizer;

import java.util.Objects;
import java.util.Scanner;

public class Tokenizer {

	private final Scanner input;

	public Tokenizer(Scanner input) {
		this.input = Objects.requireNonNull(input);
	}

	public boolean hasNext() {
		return input.hasNext();
	}

	public Token getNextToken() {
		if (input.hasNext()) {
			var data = input.next();
			var tokenType = TokenType.recognizeType(data);
			return new Token(tokenType, data);
		} else {
			throw new IllegalStateException("No tokens are available");
		}
	}

}
