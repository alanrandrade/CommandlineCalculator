package eu.gillissen.commandline.calculator;

import eu.gillissen.commandline.calculator.exception.MemoryException;
import eu.gillissen.commandline.calculator.exception.ParseException;
import eu.gillissen.commandline.calculator.memory.Memory;
import eu.gillissen.commandline.calculator.memory.Variable;
import eu.gillissen.commandline.calculator.memory.function.Function;
import eu.gillissen.commandline.calculator.node.*;
import eu.gillissen.commandline.calculator.tokenizer.Token;
import eu.gillissen.commandline.calculator.tokenizer.Tokenizer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Tokenizer tokenizer;
    private Memory memory;
    private List<Variable> tempVariables;

    public Parser(Tokenizer tokenizer, Memory memory) {
        this.tokenizer = tokenizer;
        this.memory = memory;
        tempVariables = new ArrayList<Variable>();
    }

    public BaseNode parse() throws ParseException, MemoryException {
        if(tokenizer.lineHas(Token.Type.Assign) && tokenizer.lineHas(Token.Type.Equals)) {
            return parseFunctionAssignment();
        } else if(tokenizer.lineHas(Token.Type.Equals)) {
            return parseVariableAssignmentOrEquality();
        } else {
            return parseExpression();
        }
    }

    private BaseNode parseVariableAssignmentOrEquality() throws ParseException, MemoryException {
        Token first = tokenizer.peek();
        if(tokenizer.peek().getType() == Token.Type.Literal) {
            if(memory.getVariable(tokenizer.peek().toString()) == null) {
                return parseVariableAssignment();
            }
        }
        return parseEquality(first);
    }

    private BaseNode parseEquality(Token first) throws ParseException, MemoryException {
        BaseNode left = parseExpression();
        if(tokenizer.peek().getType() == Token.Type.Equals) {
            tokenizer.read();
            BaseNode right = parseExpression();
            if(left instanceof VariableNode) {
                return new VariableAssignmentNode(first, right, memory);
            }
            return new EqualityNode(left, right);
        } else {
            throw new ParseException("=", tokenizer.read());
        }
    }

    private BaseNode parseVariableAssignment() throws ParseException, MemoryException {
        Token literal = tokenizer.read();
        if(tokenizer.peek().getType() == Token.Type.Equals) {
            tokenizer.read();
            BaseNode expression = parseExpression();
            return new VariableAssignmentNode(literal, expression, memory);
        } else {
            throw new ParseException("=", tokenizer.read());
        }
    }

    private BaseNode parseFunctionAssignment() throws ParseException, MemoryException {
        if(tokenizer.peek().getType() == Token.Type.Literal) {
            Token literal = tokenizer.read();
            tempVariables.addAll(parseFunctionVariables());
            if(tokenizer.peek().getType() == Token.Type.Assign) {
                tokenizer.read();
                if(tokenizer.peek().getType() == Token.Type.Equals) {
                    tokenizer.read();
                    BaseNode expression = parseExpression();
                    return new FunctionAssignmentNode(literal, expression, tempVariables, memory);
                } else {
                    throw new ParseException("=", tokenizer.read());
                }
            } else {
                throw new ParseException(":", tokenizer.read());
            }
        } else {
            throw new ParseException("<literal>", tokenizer.read());
        }
    }

    private List<Variable> parseFunctionVariables() throws ParseException {
        List<Variable> variables = new ArrayList<Variable>();
        if(tokenizer.peek().getType() == Token.Type.OpenBracket) {
            tokenizer.read();
            if(tokenizer.peek().getType() != Token.Type.CloseBracket) {
                variables.add(new Variable(getLiteral().toString(), BigDecimal.ZERO));
                while(tokenizer.peek().getType() != Token.Type.CloseBracket) {
                    if(tokenizer.peek().getType() == Token.Type.Comma) {
                        tokenizer.read();
                        variables.add(new Variable(getLiteral().toString(), BigDecimal.ZERO));
                    } else {
                        throw new ParseException(", or )", tokenizer.peek());
                    }
                }
            }
            tokenizer.read();
        }
        return variables;
    }

    private Token getLiteral() throws ParseException {
        if(tokenizer.peek().getType() == Token.Type.Literal) {
            return tokenizer.read();
        } else {
            throw new ParseException("<literal>", tokenizer.read());
        }
    }

    public BaseNode parseExpression() throws ParseException, MemoryException {
        BaseNode expression = parseMultiplicationOrDivision();
        while(tokenizer.peek().getType() == Token.Type.Add
                || tokenizer.peek().getType() == Token.Type.Subtract) {
            BaseNode left = expression.clone();
            Token mod = parsePlusOrMinus();
            BaseNode right = parseMultiplicationOrDivision();
            expression = new AdditionOrSubtractionNode(left, mod, right);
        }
        return expression;
    }

    private BaseNode parseMultiplicationOrDivision() throws ParseException, MemoryException {
        BaseNode expression = parseModulo();
        while(tokenizer.peek().getType() == Token.Type.Multiply
                || tokenizer.peek().getType() == Token.Type.Divide) {
            BaseNode left = expression.clone();
            Token mod = parseTimesOrDivide();
            BaseNode right = parseModulo();
            expression = new MultiplicationOrDivisionNode(left, mod, right);
        }
        return expression;
    }

    private BaseNode parseModulo() throws ParseException, MemoryException {
        BaseNode expression = parsePower();
        if(tokenizer.peek().getType() == Token.Type.Modulo) {
            Token mod = tokenizer.read();
            BaseNode modulo = parsePower();
            return new ModuloNode(expression, mod, modulo);
        }
        return expression;
    }

    private BaseNode parsePower() throws ParseException, MemoryException {
        BaseNode expression = parseBracketsNumberOrFunction();
        if(tokenizer.peek().getType() == Token.Type.Power) {
            Token mod = tokenizer.read();
            BaseNode power = parseBracketsNumberOrFunction();
            return new PowerNode(expression, mod, power);
        }
        return expression;
    }

    private BaseNode parseBracketsNumberOrFunction() throws ParseException, MemoryException {
        if(tokenizer.peek().getType() == Token.Type.OpenBracket) {
            return parseBrackets();
        } else if(tokenizer.peek().getType() == Token.Type.Literal) {
            return parseFunctionOrVariable();
        } else if(tokenizer.peek().getType() == Token.Type.VerticalBar) {
            return parseAbsolute();
        } else if(tokenizer.peek().getType() == Token.Type.Subtract) {
            tokenizer.read();
            BaseNode number = parseNumber();
            return new NegativeNumberNode(number);
        } else {
            return parseNumber();
        }
    }

    private BaseNode parseFunctionOrVariable() throws ParseException, MemoryException {
        Token literal = tokenizer.read();
        if(tokenizer.peek().getType() == Token.Type.OpenBracket) {
            return parseFunction(literal);
        } else {
            return parseVariable(literal);
        }
    }

    private BaseNode parseAbsolute() throws ParseException, MemoryException {
        tokenizer.read();
        BaseNode expression = parseExpression();
        if(tokenizer.peek().getType() == Token.Type.VerticalBar) {
            tokenizer.read();
            return new AbsoluteNode(expression);
        } else {
            throw new ParseException("|", tokenizer.peek());
        }
    }

    private BaseNode parseBrackets() throws ParseException, MemoryException {
        tokenizer.read();
        BaseNode expression = parseExpression();
        if(tokenizer.peek().getType() == Token.Type.CloseBracket) {
            tokenizer.read();
            return new BracketNode(expression);
        } else {
            throw new ParseException(")", tokenizer.peek());
        }
    }

    private BaseNode parseVariable(Token literal) throws MemoryException {
        Variable variable = getTemp(literal);
        if(variable == null) {
            variable = memory.getVariable(literal.toString());
            if(variable == null) {
                throw new MemoryException(literal.toString());
            }
        }
        return new VariableNode(variable);
    }

    private Variable getTemp(Token literal) {
        for(Variable variable : tempVariables) {
            if(variable.getName().equals(literal.toString())) {
                return variable;
            }
        }
        return null;
    }

    private BaseNode parseFunction(Token literal) throws ParseException, MemoryException {
        tokenizer.read();
        Function function = memory.getFunction(literal.toString());
        if(function == null) {
            throw new MemoryException(literal.toString());
        }
        ArrayList<BaseNode> arguments = parseFunctionArguments();
        return new FunctionNode(function, arguments);
    }

    private ArrayList<BaseNode> parseFunctionArguments() throws ParseException, MemoryException {
        ArrayList<BaseNode> arguments = new ArrayList<BaseNode>();
        if(tokenizer.peek().getType() != Token.Type.CloseBracket) {
            arguments.add(parseExpression());
            while(tokenizer.peek().getType() != Token.Type.CloseBracket) {
                if(tokenizer.peek().getType() == Token.Type.Comma) {
                    tokenizer.read();
                    arguments.add(parseExpression());
                } else {
                    throw new ParseException(", or )", tokenizer.peek());
                }
            }
        }
        tokenizer.read();
        return arguments;
    }

    private BaseNode parseNumber() throws ParseException {
        if(tokenizer.peek().getType() == Token.Type.Number) {
            Token number = tokenizer.read();
            if(tokenizer.peek().getType() == Token.Type.Dot) {
                tokenizer.read();
                if(tokenizer.peek().getType() == Token.Type.Number) {
                    return new NumberNode(number, tokenizer.read());
                } else {
                    throw new ParseException("number", tokenizer.peek());
                }
            } else {
                return new NumberNode(number);
            }
        } else {
            throw new ParseException("number", tokenizer.peek());
        }
    }

    private Token parseTimesOrDivide() throws ParseException {
        Token.Type tokenType = tokenizer.peek().getType();
        if(tokenType == Token.Type.Multiply || tokenType == Token.Type.Divide) {
            return tokenizer.read();
        } else {
            throw new ParseException("* or /", tokenizer.peek());
        }
    }

    private Token parsePlusOrMinus() throws ParseException {
        Token.Type tokenType = tokenizer.peek().getType();
        if(tokenType == Token.Type.Add || tokenType == Token.Type.Subtract) {
            return tokenizer.read();
        } else {
            throw new ParseException("- or +", tokenizer.peek());
        }
    }
}
