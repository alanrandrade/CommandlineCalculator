package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.NumberFormatter;
import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

public class EqualityNode extends BaseNode {

    private BaseNode right;
    private BaseNode left;

    public EqualityNode(BaseNode left, BaseNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public BaseNode clone() {
        return new EqualityNode(left, right);
    }

    @Override
    public String toString() {
        return toString(left, right);
    }

    @Override
    public BigDecimal evaluate() throws EvaluationException {
        BigDecimal leftValue = left.evaluate();
        BigDecimal rightValue = right.evaluate();
        value = evaluate(leftValue, rightValue);
        NumberFormatter f = NumberFormatter.getInstance();
        if(Calc.VERBOSE) {
            System.out.printf("%s = %s\n", toString(f.format(leftValue), f.format(rightValue)),
                    f.format(value));
        }
        return value;
    }

    @Override
    protected String toString(Object... args) {
        return String.format("[%s = %s]", args[0], args[1]);
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        return BigDecimal.valueOf(args[0].compareTo(args[1]));
    }

}
