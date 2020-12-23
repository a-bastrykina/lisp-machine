package ru.nsu.fit.lispmachine.tokenizer.rules;

public class CharTokenRule implements ITokenRule {
	private final String ch;

	public CharTokenRule(Character ch) {
		this.ch = Character.toString(ch);
	}

	@Override public boolean matches(String input) {
		return input.equals(ch);
	}

}
