
package ru.nsu.fit.lispmachine.tokenizer;

import static ru.nsu.fit.lispmachine.tokenizer.TokenRegex.*;

public enum TokenType {
	EOF(EofRule.INSTANCE),
	OPEN_BRACE(new WordTokenRule("(")),
	CLOSE_BRACE(new WordTokenRule(")")),
	DEFINE_KEYWORD(new WordTokenRule("define")),
	BEGIN_KEYWORD(new WordTokenRule("begin")),
	LET_SYNTAX_KEYWORD(new WordTokenRule("let-syntax")),
	LETREC_SYNTAX_KEYWORD(new WordTokenRule("letrec-syntax")),
	DEFINE_SYNTAX_KEYWORD(new WordTokenRule("define-syntax")),
	QUOTE_KEYWORD(new WordTokenRule("quote")),
	QUOTE_SYMBOL(new WordTokenRule("'")),
	LAMBDA_KEYWORD(new WordTokenRule("lambda")),
	IF_KEYWORD(new WordTokenRule("if")),
	SET_KEYWORD(new WordTokenRule("set!")),

	IDENTIFIER(new RegexTokenRule(IDENTIFIER_REGEX)),
	BOOLEAN_VALUE(new RegexTokenRule(BOOLEAN_VALUE_REGEX)),
	CHARACTER_VALUE(new RegexTokenRule(CHAR_VALUE_REGEX)),
	STRING_VALUE(new RegexTokenRule(STRING_VALUE_REGEX)),
	NUM2_VALUE(new RegexTokenRule(NUM2_VALUE_REGEX)),
	NUM8_VALUE(new RegexTokenRule(NUM8_VALUE_REGEX)),
	NUM10_VALUE(new RegexTokenRule(NUM10_VALUE_REGEX)),
	NUM16_VALUE(new RegexTokenRule(NUM16_VALUE_REGEX));

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
