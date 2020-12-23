package ru.nsu.fit.lispmachine.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.nsu.fit.lispmachine.exceptions.ParseException;
import ru.nsu.fit.lispmachine.machine.interpreter.Application;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemerString;
import ru.nsu.fit.lispmachine.tokenizer.token.Token;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

public class Parser {

	private final Iterator<Token> tokens;
	Token currentToken;

	public Parser(Iterator<Token> tokens) {
		this.tokens = tokens;
		currentToken = tokens.next();
	}

	public List<Expression> parse() {
		List<Expression> result = new ArrayList<>();
		Expression current;
		while ((current = parseNext()) != null) {
			result.add(current);
		}
		return result;
	}

	private void proceedToken() {
		if (tokens.hasNext()) {
			currentToken = tokens.next();
		} else if (currentToken.getType() != TokenType.EOF) {
			throw new ParseException("Expecting EOF token at the end of input");
		}
	}

	private Expression parseNext() {
		try {
			switch (currentToken.getType()) {
				case EOF:
					return null;
				case OPEN_BRACE:
					return parseApplication();
				//				case VECTOR_START:
				//					break;
				//				case ABBREVIATION:
				//					break;
				//				case CLOSE_BRACE:
				//					break;
				//				case IDENTIFIER:
				//					break;
				//				case BOOLEAN_VALUE:
				//					break;
				//				case CHARACTER_VALUE:
				//					break;
				//				case STRING_VALUE:
				//					break;
				case NUM2_VALUE:
					return parseBinaryNumber();
				case NUM8_VALUE:
					return parseOctetNumber();
				case NUM10_VALUE:
					return parseDecimalNumber();
				case NUM16_VALUE:
					return parseHexNumber();
				case REAL_VALUE:
					return parseRealNumber();
				default:
					return new SchemerString(currentToken.getData());
			}
		} finally {
			proceedToken();
		}
	}

	private Expression parseApplication() {
		proceedToken();
		Expression operator = parseNext();
		List<Expression> operands = new ArrayList<>();
		Expression current;
		while (currentToken.getType() != TokenType.CLOSE_BRACE) {
			current = parseNext();
			if (current == null) {
				throw new ParseException("Expecting closing brace, but EOF reached");
			}
			operands.add(current);
		}
		return new Application(operator, operands);
	}

	private Expression parseDecimalNumber() {
		return new SchemeNumber(Integer.parseInt(currentToken.getData()));
	}

	private Expression parseBinaryNumber() {
		return new SchemeNumber(Integer.parseInt(currentToken.getData().substring(2), 2));
	}

	private Expression parseOctetNumber() {
		return new SchemeNumber(Integer.parseInt(currentToken.getData().substring(2), 8));
	}

	private Expression parseHexNumber() {
		return new SchemeNumber(Integer.parseInt(currentToken.getData().substring(2), 16));
	}

	private Expression parseRealNumber() {
		return new SchemeNumber(Double.parseDouble(currentToken.getData()));
	}
}
