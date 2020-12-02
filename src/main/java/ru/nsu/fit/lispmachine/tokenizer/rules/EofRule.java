package ru.nsu.fit.lispmachine.tokenizer.rules;

public class EofRule implements ITokenRule {
	public static EofRule INSTANCE = new EofRule();

	private EofRule() {};

	@Override public boolean matches(String input) {
		return false;
	}
}
