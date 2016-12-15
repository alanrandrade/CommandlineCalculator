package eu.gillissen.commandline.calculator;

/**
 * @author Sasja Gillissen
 */
public class Expression {
    public Expression(ExpressionType type, Expression left, Expression right) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = null;
    }

    public Expression(int value) {
        this.type = ExpressionType.VALUE;
        this.left = null;
        this.right = null;
        this.value = value;
    }

    @Override
    public String toString() {
        if (type == ExpressionType.VALUE) {
            return String.valueOf(value);
        } else {
            return String.format("(%s %s %s)", left.toString(), type.toString(), right.toString());
        }
    }

    public int eval() {
        if (type == ExpressionType.VALUE) {
            return value;
        } else {
            return type.apply(left.eval(), right.eval());
        }
    }

    private ExpressionType type;
    private Expression left;
    private Expression right;
    private Integer value;

    public ExpressionType getType() {
        return type;
    }

    public void setType(ExpressionType type) {
        this.type = type;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
