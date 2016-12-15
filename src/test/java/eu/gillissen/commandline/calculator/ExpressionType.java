package eu.gillissen.commandline.calculator;

/**
 * @author Sasja Gillissen
 */
public enum ExpressionType {
    VALUE,
    TIMES,
    PLUS,
    MINUS;

    @Override
    public String toString() {
        switch (this) {
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case TIMES:
                return "*";
            case VALUE:
            default:
                return "";
        }
    }

    public int apply(int left, int right) {
        switch (this) {
            case PLUS:
                return left + right;
            case MINUS:
                return left - right;
            case TIMES:
                return left * right;
            case VALUE:
            default:
                return -1;
        }
    }
}
