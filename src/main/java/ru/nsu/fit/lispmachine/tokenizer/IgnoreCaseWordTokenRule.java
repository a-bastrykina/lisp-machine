package ru.nsu.fit.lispmachine.tokenizer;

public class IgnoreCaseWordTokenRule extends WordTokenRule {
	public IgnoreCaseWordTokenRule(String word) {
		super(word.toLowerCase());
	}

	@Override public boolean matches(String input) {
		return super.matches(input.toLowerCase());
	}
}
