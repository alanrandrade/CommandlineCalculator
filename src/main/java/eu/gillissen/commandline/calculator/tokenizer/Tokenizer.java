package eu.gillissen.commandline.calculator.tokenizer;

import eu.gillissen.commandline.calculator.tokenizer.Token.Type;

public class Tokenizer {
    private final String line;
    private int position;
    private Token currentToken;

    public Tokenizer(String line) {
        this.line = line;
        this.position = 0;
        this.currentToken = null;
    }

    public Token peek() {
        if(currentToken == null) {
            currentToken = nextToken();
        }
        return currentToken;
    }

    public Token read() {
        Token token = peek();
        currentToken = null;
        return token;
    }

    private Token nextToken() {
        if(position >= line.length()) {
            return new Token(Token.Type.EOF, position);
        } else {
            while(line.charAt(position) == ' ') {
                position++;
            }
            char character = line.charAt(position);
            for(Token.Type type : Token.Type.values()) {
                if(character != Token.EMPTY_TOKEN_TYPE_VALUE && character == type.getValue()) {
                    return new Token(type, position++);
                }
            }
            if(isCharacter()) {
                return readLiteral();
            } else if(isNumber()) {
                return readNumber();
            } else {
                throw new RuntimeException(String.format("Token '%c' unexpected", character));
            }
        }
    }

    private Token readLiteral() {
        int start = position;
        do {
            position++;
        } while(position < line.length() && isCharacter());
        return new Token(Token.Type.Literal, line.substring(start, position), start);
    }

    private Token readNumber() {
        int start = position;
        do {
            position++;
        } while(position < line.length() && isNumber());
        return new Token(Token.Type.Number, line.substring(start, position), start);
    }

    private boolean isCharacter() {
        char c = line.charAt(position);
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
    }

    private boolean isNumber() {
        char c = line.charAt(position);
        return c >= '0' && c <= '9';
    }

    public boolean lineHas(Type tokenType) {
        return line.indexOf(tokenType.getValue()) != - 1;
    }

}
