package ru.nsu.fit.lispmachine.tokenizer.rules;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Char token rule
 */
public class CharTokenRule implements ITokenRule {
	private final List<String> chars;

	/**
	 * @param chars list of characters
	 */
	public CharTokenRule(Character... chars) {
		this.chars = Arrays.stream(chars).map(Object::toString).collect(Collectors.toList());
	}

	@Override public boolean matches(String input) {
		return chars.stream().anyMatch((ch) -> ch.equals(input));
	}

}
