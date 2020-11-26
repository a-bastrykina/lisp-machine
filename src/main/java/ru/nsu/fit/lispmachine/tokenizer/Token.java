package ru.nsu.fit.lispmachine.tokenizer;

import java.util.Objects;

public class Token {
	private final TokenType type;
	private final String data;

	public Token(TokenType type, String data) {
		this.type = Objects.requireNonNull(type);
		this.data = Objects.requireNonNull(data);
	}
}
