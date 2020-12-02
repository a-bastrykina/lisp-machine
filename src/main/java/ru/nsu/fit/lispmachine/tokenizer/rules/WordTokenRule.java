package ru.nsu.fit.lispmachine.tokenizer.rules;

import java.util.Objects;

public class WordTokenRule implements ITokenRule {
	private final String word;

	public WordTokenRule(String word) {
		this.word = Objects.requireNonNull(word);
	}

	@Override public boolean matches(String input) {
		return word.equals(input);
	}
}
