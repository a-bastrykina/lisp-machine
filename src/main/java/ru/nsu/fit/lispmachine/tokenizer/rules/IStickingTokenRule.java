package ru.nsu.fit.lispmachine.tokenizer.rules;

public interface IStickingTokenRule extends ITokenRule {
	String cutOff(String input);
}
