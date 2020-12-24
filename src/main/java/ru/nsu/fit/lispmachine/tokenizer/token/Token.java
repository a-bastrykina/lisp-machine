package ru.nsu.fit.lispmachine.tokenizer.token;

import java.util.Objects;

public class Token {
	private final TokenType type;
	private final String data;

	public Token(TokenType type, String data) {
		this.type = Objects.requireNonNull(type);
		this.data = data;
	}

	public Token(TokenType type) {
		this(type, null);
	}

	public TokenType getType() {
		return type;
	}

	public String getData() {
		return data;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Token token = (Token) o;
		return type == token.type &&
				Objects.equals(data, token.data);
	}

	@Override public int hashCode() {
		return Objects.hash(type, data);
	}
}
