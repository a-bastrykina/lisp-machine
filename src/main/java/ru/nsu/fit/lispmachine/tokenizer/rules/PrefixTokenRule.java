package ru.nsu.fit.lispmachine.tokenizer.rules;

public class PrefixTokenRule implements IStickingTokenRule {

	private String prefix;

	public PrefixTokenRule(String prefix) {
		this.prefix = prefix;
	}

	@Override public boolean matches(String input) {
		return input.startsWith(prefix);
	}

	@Override public String cutOff(String input) {
		if (!matches(input)) {
			throw new IllegalArgumentException(String.format("%s does not match the token rule!"));
		}
		return input.substring(prefix.length());
	}
}
