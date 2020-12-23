package ru.nsu.fit.lispmachine.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import ru.nsu.fit.lispmachine.exceptions.ParseException;
import ru.nsu.fit.lispmachine.machine.interpreter.Application;
import ru.nsu.fit.lispmachine.machine.interpreter.Assignment;
import ru.nsu.fit.lispmachine.machine.interpreter.Define;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.IfClause;
import ru.nsu.fit.lispmachine.machine.interpreter.Lambda;
import ru.nsu.fit.lispmachine.machine.interpreter.QuotedExpr;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeBool;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeChar;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeStringLiteral;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemerString;
import ru.nsu.fit.lispmachine.tokenizer.token.Token;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

public class Parser {

	private final HashMap<String, Supplier<Expression>> definedForms = new HashMap<>();

	private final Iterator<Token> tokens;
	Token currentToken;

	public Parser(Iterator<Token> tokens) {
		this.tokens = tokens;
		currentToken = tokens.next();
		initDefinedForms();
	}

	private void initDefinedForms() {
		definedForms.put(SchemeKeywords.LAMBDA_KEYWORD, this::parseLambda);
		definedForms.put(SchemeKeywords.DEFINE_KEYWORD, this::parseDefine);
		definedForms.put(SchemeKeywords.IF_KEYWORD, this::parseIf);
		definedForms.put(SchemeKeywords.QUOTE_KEYWORD, this::parseQuote);
		definedForms.put(SchemeKeywords.SET_KEYWORD, this::parseAssignment);
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
					return parseAfterOpenBrace();
				//				case VECTOR_START:
				//					break;
				case QUOTE:
					return parseQuote();
				//				case CLOSE_BRACE:
				//					break;
				//				case IDENTIFIER:
				//					break;
				case BOOLEAN_VALUE:
					return parseBoolean();
				case CHARACTER_VALUE:
					return parseChar();
				case STRING_VALUE:
					return parseString();
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

	private Expression parseAfterOpenBrace() {
		proceedToken();
		if (currentToken.getType() == TokenType.IDENTIFIER) {
			Supplier<Expression> supplier = definedForms.get(currentToken.getData());
			if (supplier != null) {
				return supplier.get();
			}
		}
		return parseApplication();
	}

	private Expression parseDefine() {
		proceedToken();

		if (currentToken.getType() != TokenType.OPEN_BRACE) {
			// Try parse define without parameters.
			if (currentToken.getType() != TokenType.IDENTIFIER) {
				throw new ParseException("Failed to parse define clause: expected ( or identifier.");
			}
			SchemerString definitionName = (SchemerString) parseNext();
			Expression definitionExpr = parseNext();

			if (currentToken.getType() != TokenType.CLOSE_BRACE) {
				throw new ParseException("Expecting closing brace to complete define");
			}
			return new Define(definitionName, definitionExpr);
		} else {
			proceedToken();

			if (currentToken.getType() != TokenType.IDENTIFIER) {
				throw new ParseException("Expecting identifier in define expression");
			}

			SchemerString definitionName = (SchemerString) parseNext();
			List<Expression> params = new ArrayList<>();
			Expression currentExpr;

			while (currentToken.getType() != TokenType.CLOSE_BRACE) {
				currentExpr = parseNext();
				if (currentExpr == null) {
					throw new ParseException("Expecting closing brace, but EOF reached");
				}
				params.add(currentExpr);
			}

			proceedToken();

			currentExpr = parseNext();

			if (currentToken.getType() != TokenType.CLOSE_BRACE) {
				throw new ParseException("Expecting closing brace to complete define");
			}

			return new Define(definitionName, params, currentExpr);
		}
	}

	private Expression parseLambda() {
		proceedToken();

		if (currentToken.getType() != TokenType.OPEN_BRACE) {
			throw new ParseException("Expecting open brace to parse lambda's arguments");
		}
		proceedToken();

		List<Expression> args = new ArrayList<>();
		Expression currentExpr;
		while (currentToken.getType() != TokenType.CLOSE_BRACE) {
			currentExpr = parseNext();
			if (currentExpr == null) {
				throw new ParseException("Expecting closing brace, but EOF reached");
			}
			args.add(currentExpr);
		}
		proceedToken();

		currentExpr = parseNext();

		if (currentToken.getType() != TokenType.CLOSE_BRACE) {
			throw new ParseException("Expecting closing brace at the end of lambda");
		}

		return new Lambda(args, currentExpr);
	}

	private Expression parseIf() {
		proceedToken();

		Expression condExpr = parseNext();
		Expression thenExpr = parseNext();
		Expression elseExpr = parseNext();

		if (currentToken.getType() != TokenType.CLOSE_BRACE) {
			throw new ParseException("Expecting closing brace to complete if-clause");
		}

		return new IfClause(condExpr, thenExpr, elseExpr);
	}

	private Expression parseQuote() {
		boolean expectCloseBrace = currentToken.getType() == TokenType.IDENTIFIER;

		proceedToken();

		Expression nestedExpr = parseNext();

		if (expectCloseBrace && currentToken.getType() != TokenType.CLOSE_BRACE) {
			throw new ParseException("Expecting closing brace to complete quote clause");
		}

		return new QuotedExpr(nestedExpr);
	}

	private Expression parseAssignment() {
		proceedToken();

		if (currentToken.getType() != TokenType.IDENTIFIER) {
			throw new ParseException("Expecting identifier to parse assignment expression");
		}

		Expression name = parseNext();
		Expression value = parseNext();

		if (currentToken.getType() != TokenType.CLOSE_BRACE) {
			throw new ParseException("Expecting closing brace to complete assignment clause");
		}

		return new Assignment((SchemerString) name, value);
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

	private Expression parseBoolean() {
		switch (currentToken.getData().substring(1).toLowerCase()) {
			case "f":
				return new SchemeBool(false);
			case "t":
				return new SchemeBool(true);
			default:
				throw new ParseException("Failed to parse boolean for input" + currentToken.getData());
		}
	}

	private Expression parseChar() {
		String rawValue = currentToken.getData().substring(2);
		switch (rawValue) {
			case "newline":
				return new SchemeChar('\n');
			case "space":
				return new SchemeChar(' ');
			default:
				return new SchemeChar(rawValue.charAt(0));
		}
	}

	private Expression parseString() {
		String unwrapped = currentToken.getData().substring(1, currentToken.getData().length() - 1);
		return new SchemeStringLiteral(unwrapped);
	}

}
