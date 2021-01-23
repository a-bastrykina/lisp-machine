
package ru.nsu.fit.lispmachine.tokenizer.token;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ru.nsu.fit.lispmachine.tokenizer.rules.CharTokenRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.EofRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.ITokenRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.RegexTokenRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.WordTokenRule;

import static ru.nsu.fit.lispmachine.tokenizer.token.TokenRegex.*;

/**
 * Token type enum.
 */
public enum TokenType {
	EOF(EofRule.INSTANCE),

	OPEN_BRACE(new CharTokenRule('(')),
	VECTOR_START(new WordTokenRule("#(")),
	QUOTE(new CharTokenRule('\'')),
	ABBREVIATION(new CharTokenRule('`', ',')),
	CLOSE_BRACE(new CharTokenRule(')')),

	IDENTIFIER(new RegexTokenRule(IDENTIFIER_REGEX)),
	BOOLEAN_VALUE(new RegexTokenRule(BOOLEAN_VALUE_REGEX)),
	CHARACTER_VALUE(new RegexTokenRule(CHAR_VALUE_REGEX)),
	STRING_VALUE(new RegexTokenRule(STRING_VALUE_REGEX)),
	NUM2_VALUE(new RegexTokenRule(NUM2_VALUE_REGEX)),
	NUM8_VALUE(new RegexTokenRule(NUM8_VALUE_REGEX)),
	NUM10_VALUE(new RegexTokenRule(NUM10_VALUE_REGEX)),
	NUM16_VALUE(new RegexTokenRule(NUM16_VALUE_REGEX)),
	REAL_VALUE(new RegexTokenRule(REAL_VALUE_REGEX));

	private final ITokenRule rule;

	private static List<TokenType> charBasedRuleTypes;

	TokenType(ITokenRule rule) {
		this.rule = rule;
	}

	ITokenRule getRule() {
		return this.rule;
	}

	private static List<TokenType> getCharacterBasedRuleTypes() {
		if (charBasedRuleTypes == null) {
			charBasedRuleTypes = Arrays.stream(values()).filter((t) -> t.getRule() instanceof CharTokenRule)
					.collect(Collectors.toList());
		}
		return charBasedRuleTypes;
	}

	/**
	 * Try to recognize token type which
	 * corresponds to the input string.
	 * @param input input string
	 * @return token type
	 * @throws IllegalArgumentException thrown when type cannot be recognized
	 */
	public static TokenType recognizeType(String input) {
		for (var type : TokenType.values()) {
			if (type.rule.matches(input)) {
				return type;
			}
		}
		throw new IllegalArgumentException(String.format("Token type for input '%s' is not recognized", input));
	}

	/**
	 * Try to recognize token type which
	 * corresponds to the input character.
	 * @param ch input char
	 * @return token type
	 * @throws IllegalArgumentException thrown when type cannot be recognized
	 */
	public static TokenType recognizeType(int ch) {
		String input = Character.toString(ch);
		for (var type : TokenType.getCharacterBasedRuleTypes()) {
			if (type.getRule().matches(input))
				return type;
		}
		return null;
	}

}
