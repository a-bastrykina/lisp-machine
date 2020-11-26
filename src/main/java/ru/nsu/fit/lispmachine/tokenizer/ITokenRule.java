package ru.nsu.fit.lispmachine.tokenizer;

public interface ITokenRule {
	boolean matches(String input);
}
