package eu.gillissen.commandline.calculator.tokenizer;

public class Token {

    public static final char EMPTY_TOKEN_TYPE_VALUE = ' ';

    private Type type;
    private String value;
    private int position;

    Token(Type type, String value, int position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    public Token(Type type, int position) {
        this.type = type;
        this.value = type.toString();
        this.position = position;
    }

    @Override
    public String toString() {
        return value;
    }

    public int getPosition() {
        return position;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        Number(EMPTY_TOKEN_TYPE_VALUE), Literal(EMPTY_TOKEN_TYPE_VALUE), OpenBracket('('), CloseBracket(
                ')'), Multiply('*'), Divide('/'), Subtract('-'), Add('+'), EOF(
                EMPTY_TOKEN_TYPE_VALUE), Power('^'), Modulo('%'), Dot('.'), Comma(','), VerticalBar(
                '|'), Assign(':'), Equals('='), String('"');

        private final char value;

        Type(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }

        @Override
        public String toString() {
            if (value == EMPTY_TOKEN_TYPE_VALUE) {
                return "";
            }
            return Character.toString(value);
        }

    }
}
