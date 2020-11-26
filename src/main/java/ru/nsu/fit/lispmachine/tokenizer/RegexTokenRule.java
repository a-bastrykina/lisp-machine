package ru.nsu.fit.lispmachine.tokenizer;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegexTokenRule implements ITokenRule {
	private final Pattern regex;

	public RegexTokenRule(String regex) {
		Objects.requireNonNull(regex);
		this.regex = Pattern.compile(regex);
	}

	@Override public boolean matches(String input) {
		return regex.matcher(input).matches();
	}
}
