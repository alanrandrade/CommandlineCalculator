package eu.gillissen.commandline.calculator.exception;

import eu.gillissen.commandline.calculator.tokenizer.Token;

public class ParseException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ParseException(String expected, Token token) {
        super(String.format("Expected: '%s' Actual:'%s' at position %d", expected,
                token.toString(), token.getPosition()));
    }
}
