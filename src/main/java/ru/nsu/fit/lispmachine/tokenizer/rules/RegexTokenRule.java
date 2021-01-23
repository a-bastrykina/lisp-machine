package ru.nsu.fit.lispmachine.tokenizer.rules;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Regular expression rule.
 */
public class RegexTokenRule implements ITokenRule {
	private final Pattern regex;

	/**
	 * @param regex
	 * @throws NullPointerException if argument is null
	 * @throws PatternSyntaxException if the regex syntax is invalid
	 */
	public RegexTokenRule(String regex) {
		Objects.requireNonNull(regex);
		this.regex = Pattern.compile(regex);
	}

	@Override public boolean matches(String input) {
		return regex.matcher(input).matches();
	}

}
