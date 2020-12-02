package ru.nsu.fit.lispmachine.tokenizer.rules;

public class SuffixTokenRule implements IStickingTokenRule {
	private final String suffix;

	public SuffixTokenRule(String suffix) {
		this.suffix = suffix;
	}

	@Override public boolean matches(String input) {
		return input.endsWith(suffix);
	}

	@Override public String cutOff(String input) {
		if (!matches(input)) {
			throw new IllegalArgumentException(String.format("%s does not match the token rule!"));
		}
		return input.substring(0, input.length() - suffix.length());
	}
}
