package ru.nsu.fit.lispmachine.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import ru.nsu.fit.lispmachine.exceptions.ParseException;
import ru.nsu.fit.lispmachine.machine.interpreter.Application;
import ru.nsu.fit.lispmachine.machine.interpreter.Assignment;
import ru.nsu.fit.lispmachine.machine.interpreter.Begin;
import ru.nsu.fit.lispmachine.machine.interpreter.Define;
import ru.nsu.fit.lispmachine.machine.interpreter.DoTimes;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.IfClause;
import ru.nsu.fit.lispmachine.machine.interpreter.Lambda;
import ru.nsu.fit.lispmachine.machine.interpreter.QuotedExpr;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeBool;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeChar;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeList;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeString;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeIdentifier;
import ru.nsu.fit.lispmachine.machine.interpreter.TryCatch;
import ru.nsu.fit.lispmachine.machine.interpreter.native_calls.JavaMethodCall;
import ru.nsu.fit.lispmachine.tokenizer.token.Token;
import ru.nsu.fit.lispmachine.tokenizer.token.TokenType;

public class Parser {

	private final HashMap<String, Supplier<Expression>> definedForms = new HashMap<>();

	private final Iterator<Token> tokens;
	Token currentToken;

	public Parser(Iterator<Token> tokens) {
		this.tokens = Objects.requireNonNull(tokens);
		currentToken = tokens.next();
		initDefinedForms();
	}

	private void initDefinedForms() {
		definedForms.put(SchemeKeywords.LAMBDA_KEYWORD, this::parseLambda);
		definedForms.put(SchemeKeywords.DEFINE_KEYWORD, this::parseDefine);
		definedForms.put(SchemeKeywords.IF_KEYWORD, this::parseIf);
		definedForms.put(SchemeKeywords.QUOTE_KEYWORD, this::parseQuote);
		definedForms.put(SchemeKeywords.SET_KEYWORD, this::parseAssignment);
		definedForms.put(SchemeKeywords.BEGIN_KEYWORD, this::parseBegin);
		definedForms.put(SchemeKeywords.JAVA_CALL_KEYWORD, this::parseJavaMethodCall);
		definedForms.put(SchemeKeywords.TRY_CATCH_KEYWORD, this::parseTryCatch);
		definedForms.put(SchemeKeywords.DO_TIMES_KEYWORD, this::parseDoTimes);
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
				case CLOSE_BRACE:
					return null;
				case OPEN_BRACE:
					return parseAfterOpenBrace();
				case QUOTE:
					return parseQuote();
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
					return new SchemeIdentifier(currentToken.getData());
			}
		} finally {
			proceedToken();
		}
	}

	private List<Expression> parseExpressionsList() {
		List<Expression> result = new ArrayList<>();
		while (currentToken.getType() != TokenType.CLOSE_BRACE) {
			var currentExpr = parseNext();
			if (currentExpr == null) {
				throw new ParseException("Expecting closing brace");
			}
			result.add(currentExpr);
		}
		return result;
	}

	private Expression parseApplication() {
		Expression operator = parseNext();
		List<Expression> operands = parseExpressionsList();
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
			SchemeIdentifier definitionName = (SchemeIdentifier) parseNext();
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

			SchemeIdentifier definitionName = (SchemeIdentifier) parseNext();
			var params = parseExpressionsList();
			proceedToken();
			var expressions = parseExpressionsList();
			return new Define(definitionName, params, expressions);
		}
	}

	private Expression parseLambda() {
		proceedToken();

		if (currentToken.getType() != TokenType.OPEN_BRACE) {
			throw new ParseException("Expecting open brace to parse lambda's arguments");
		}

		proceedToken();
		List<Expression> args = parseExpressionsList();
		proceedToken();
		Expression body = parseNext();

		if (currentToken.getType() != TokenType.CLOSE_BRACE) {
			throw new ParseException("Expecting closing brace at the end of lambda");
		}

		return new Lambda(args, body);
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
		boolean expectCloseBrace = "quote".equals(currentToken.getData());
		proceedToken();

		if (currentToken.getType() == TokenType.OPEN_BRACE) {
			proceedToken();
			List<Expression> args = parseExpressionsList();
			return new QuotedExpr(new SchemeList(args));
		} else {
			Expression nestedExpr = parseAtom();
			if (expectCloseBrace) {
				proceedToken();
				if (currentToken.getType() != TokenType.CLOSE_BRACE) {
					throw new ParseException("Expecting closing brace to complete quote clause");
				}
			}
			return new QuotedExpr(nestedExpr);
		}
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

		return new Assignment((SchemeIdentifier) name, value);
	}

	private Expression parseBegin() {
		proceedToken();
		List<Expression> operands = parseExpressionsList();
		return new Begin(operands);
	}

	private Expression parseJavaMethodCall() {
		proceedToken();
		if (currentToken.getType() != TokenType.STRING_VALUE) {
			throw new ParseException("Expecting string value to parse java method call");
		}

		SchemeString className = (SchemeString) parseNext();
		if (currentToken.getType() != TokenType.STRING_VALUE) {
			throw new ParseException("Expecting string value to parse java method call");
		}

		SchemeString methodName = (SchemeString) parseNext();
		List<Expression> args = parseExpressionsList();

		return new JavaMethodCall(className, methodName, args);
	}

	private Expression parseTryCatch() {
		proceedToken();

		Expression tryBody = parseNext();
		Map<SchemeString, Expression> namesAndCatches = new HashMap<>();

		while (currentToken.getType() != TokenType.CLOSE_BRACE) {
			if (currentToken.getType() != TokenType.STRING_VALUE) {
				throw new ParseException("Expecting string value to parse try-catch clause");
			}
			var name = (SchemeString) parseNext();
			var catchBody = parseNext();
			if (catchBody == null) {
				throw new ParseException("Unexpected end of try-catch clause");
			}
			namesAndCatches.put(name, catchBody);
		}

		return new TryCatch(tryBody, namesAndCatches);
	}

	private Expression parseDoTimes() {
		proceedToken();

		if (currentToken.getType() != TokenType.OPEN_BRACE) {
			throw new ParseException("Expecting open brace to parse do-times clause");
		}
		proceedToken();

		if (currentToken.getType() != TokenType.IDENTIFIER) {
			throw new ParseException("Expecting identifier to parse do-times clause");
		}
		SchemeIdentifier loopVar = (SchemeIdentifier) parseNext();

		if (currentToken.getType() != TokenType.NUM10_VALUE) {
			throw new ParseException("Expecting decimal number to parse do-times clause");
		}
		SchemeNumber iterBound = (SchemeNumber) parseNext();

		if (currentToken.getType() != TokenType.CLOSE_BRACE) {
			throw new ParseException("Expecting close brace to parse do-times clause");
		}
		proceedToken();

		Expression body = parseNext();

		if (currentToken.getType() != TokenType.CLOSE_BRACE) {
			throw new ParseException("Expecting closing brace to complete assignment clause");
		}
		return new DoTimes(loopVar, iterBound, body);
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
		return new SchemeString(unwrapped);
	}

	private Expression parseAtom() {
		switch (currentToken.getType()) {
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
			case IDENTIFIER:
				return new SchemeIdentifier(currentToken.getData());
			default:
				throw new ParseException("Couldn parse atom for the token " + currentToken.getType());
		}
	}

}
