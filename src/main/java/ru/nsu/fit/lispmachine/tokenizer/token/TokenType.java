
package ru.nsu.fit.lispmachine.tokenizer.token;

import ru.nsu.fit.lispmachine.tokenizer.rules.EofRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.ITokenRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.PrefixTokenRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.RegexTokenRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.SuffixTokenRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.WordTokenRule;

import static ru.nsu.fit.lispmachine.tokenizer.token.TokenRegex.*;

public enum TokenType {
	EOF(EofRule.INSTANCE),

	OPEN_BRACE(new PrefixTokenRule("(")),
	SHARP_PREFIX_TOKEN_RULE(new PrefixTokenRule("#")),
	ABBRIVEATION_TOKEN_RULE(new PrefixTokenRule("`")),
	CLOSE_BRACE(new SuffixTokenRule(")")),

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

	public ITokenRule getRule() {
		return this.rule;
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
