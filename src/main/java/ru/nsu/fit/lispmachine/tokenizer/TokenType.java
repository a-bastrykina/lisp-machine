
package ru.nsu.fit.lispmachine.tokenizer;

public enum TokenType {
	EOF(EofRule.INSTANCE),
	OPEN_BRACE(new WordTokenRule("(")),
	CLOSE_BRACE(new WordTokenRule(")"));

	private final ITokenRule rule;

	TokenType(ITokenRule rule) {
		this.rule = rule;
	}

	public static TokenType recognizeType(String input) {
		for (var type : TokenType.values()) {
			if (type.rule.matches(input)) {
				return type;
			}
		}
		throw new IllegalArgumentException(String.format("Token type for input '%s' is not recognized", input));
	}

}
