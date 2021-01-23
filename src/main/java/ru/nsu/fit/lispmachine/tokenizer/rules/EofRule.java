package ru.nsu.fit.lispmachine.tokenizer.rules;

public class EofRule implements ITokenRule {
	/**
	 * An instance of EofRule.
	 */
	public static EofRule INSTANCE = new EofRule();

	private EofRule() {};

	/**
	 * @param input input string
	 * @return always false
	 */
	@Override public boolean matches(String input) {
		return false;
	}
}
