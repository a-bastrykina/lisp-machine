package ru.nsu.fit.lispmachine.tokenizer.token;

class TokenRegex {
	static final String IDENTIFIER_REGEX = "[a-z!$%&*/:<=>?~_^][a-z!$%&*/:<=>?~_^0-9.+-]*|\\.\\.\\.|\\+|-";
	static final String BOOLEAN_VALUE_REGEX = "#[ftFT]";
	static final String CHAR_VALUE_REGEX = "#\\\\.|#\\\\newline|#\\\\space";
	static final String STRING_VALUE_REGEX = "\"(\"|\\\\|[^\"\\\\])*\"";
	static final String NUM2_VALUE_REGEX = "#b([01])*";
	static final String NUM8_VALUE_REGEX = "#o([0-7])*";
	static final String NUM16_VALUE_REGEX = "#x([0-9a-f])*";
	static final String NUM10_VALUE_REGEX = "[+-]?\\d+";
	static final String REAL_VALUE_REGEX = "[+-]?\\d+e[+-]\\d+|[+-]?\\.\\d+(e[+-]\\d+)?|[+-]?\\d+\\.\\d*(e[+-]\\d+)?";
}
