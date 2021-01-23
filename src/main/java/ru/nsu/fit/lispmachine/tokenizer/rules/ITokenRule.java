package ru.nsu.fit.lispmachine.tokenizer.rules;

/**
 * An interface for representing
 * a rule that a string should match to be
 * recognized as a particular type of token.
 */
public interface ITokenRule {
	/**
	 * @param input input string
	 * @return
	 */
	boolean matches(String input);
}
