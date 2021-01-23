package ru.nsu.fit.lispmachine.tokenizer.token;

import java.util.Objects;

/**
 * Class that represents token.
 */
public class Token {
	private final TokenType type;
	private final String data;

	/**
	 * @param type token type
	 * @param data string data of token
	 * @throws NullPointerException if <b>data</b>argument is null
	 */
	public Token(TokenType type, String data) {
		this.type = Objects.requireNonNull(type);
		this.data = data;
	}

	/**
	 * @param type token type
	 */
	public Token(TokenType type) {
		this(type, null);
	}

	/**
	 * @return token type
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * @return token data
	 */
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
