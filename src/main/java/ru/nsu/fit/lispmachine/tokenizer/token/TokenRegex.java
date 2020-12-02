package ru.nsu.fit.lispmachine.tokenizer.token;

public class TokenRegex {
	public static final String IDENTIFIER_REGEX = "[a-z!$%&*/:<=>?~_^][a-z!$%&*/:<=>?~_^0-9.+-]*|\\.\\.\\.|\\+|-";
	public static final String BOOLEAN_VALUE_REGEX = "[ftFT]";
	public static final String CHAR_VALUE_REGEX = "\\\\.|\\\\newline|\\\\space";
	public static final String STRING_VALUE_REGEX = "\"(\"|\\\\|[^\"\\\\])*\"";
	public static final String NUM2_VALUE_REGEX = "b[01]";
	public static final String NUM8_VALUE_REGEX = "o[0-7]";
	public static final String NUM16_VALUE_REGEX = "x[0-9a-f]";
	public static final String NUM10_VALUE_REGEX = "[+-]?\\d";
}
