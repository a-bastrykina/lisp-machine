package ru.nsu.fit.lispmachine.tokenizer.rules;

import java.util.Objects;

/**
 * Word token rule.
 */
public class WordTokenRule implements ITokenRule {
	private final String word;

	/**
	 * @param word any string
	 * @throws NullPointerException if the argument is null
	 */
	public WordTokenRule(String word) {
		this.word = Objects.requireNonNull(word);
	}

	@Override public boolean matches(String input) {
		return word.equals(input);
	}
}
