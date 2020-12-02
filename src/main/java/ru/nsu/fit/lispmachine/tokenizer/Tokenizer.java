package ru.nsu.fit.lispmachine.tokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import ru.nsu.fit.lispmachine.tokenizer.rules.IStickingTokenRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.PrefixTokenRule;
import ru.nsu.fit.lispmachine.tokenizer.rules.SuffixTokenRule;
import ru.nsu.fit.lispmachine.tokenizer.token.Token;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

public class Tokenizer {

	public static Stream<Token> tokenize(File file) throws FileNotFoundException {
		return createStream(new Scanner(file));
	}

	public static Stream<Token> tokenize(String inputString) {
		return createStream(new Scanner(inputString));
	}

	private static Stream<Token> createStream(Scanner sc) {
		var splitr = Spliterators.spliteratorUnknownSize(new TokenIterator(sc), Spliterator.NONNULL);
		return StreamSupport.stream(splitr, false).onClose(sc::close);
	}

	private static class TokenIterator implements Iterator<Token> {
		final Scanner sc;
		final Deque<Token> tokenQueue;

		TokenIterator(Scanner sc) {
			this.sc = sc;
			tokenQueue = new ArrayDeque<>();
		}

		@Override public boolean hasNext() {
			return !tokenQueue.isEmpty() || sc.hasNext();
		}

		@Override public Token next() {
			if (!tokenQueue.isEmpty()) {
				return tokenQueue.removeFirst();
			}
			if (sc.hasNext()) {
				var data = sc.next();
				TokenType tokenType;

				while ((tokenType = TokenType.recognizeType(data)).getRule() instanceof PrefixTokenRule) {
					tokenQueue.addLast(new Token(tokenType));
					data = ((IStickingTokenRule) tokenType.getRule()).cutOff(data);
				}

				List<Token> suffixTokens = new ArrayList<>();
				if (tokenType.getRule() instanceof SuffixTokenRule) {
					do {
						suffixTokens.add(new Token(tokenType));
						data = ((IStickingTokenRule) tokenType.getRule()).cutOff(data);
					} while ((tokenType = TokenType.recognizeType(data)).getRule() instanceof SuffixTokenRule);
				}

				// Now there must remain only one rule.
				tokenQueue.add(new Token(tokenType, data));
				tokenQueue.addAll(suffixTokens);

				return tokenQueue.removeFirst();
			} else {
				throw new IllegalStateException("No tokens are available");
			}
		}
	}
}
