package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.NumberFormatter;
import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.tokenizer.Token;

import java.math.BigDecimal;

public abstract class SplitNode extends BaseNode {
    Token mod;

    public SplitNode(BaseNode left, Token mod, BaseNode right) {
        this.mod = mod;
        children.add(left);
        children.add(right);
    }

    @Override
    public BigDecimal evaluate() throws EvaluationException {
        if(! isEvaluated) {
            BigDecimal leftValue = left().evaluate();
            BigDecimal rightValue = right().evaluate();
            value = evaluate(leftValue, rightValue);
            if(Calc.VERBOSE) {
                NumberFormatter f = NumberFormatter.getInstance();
                System.out.printf("%s = %s\n",
                        toString(f.format(leftValue), mod, f.format(rightValue)), f.format(value));
            }
            isEvaluated = true;
        }
        return value;
    }

    protected BaseNode left() {
        return getChildren().get(0);
    }

    protected BaseNode right() {
        return getChildren().get(1);
    }

    @Override
    public String toString() {
        return toString(left(), mod, right());
    }

    protected String toString(Object... args) {
        return String.format("%s %s %s", args[0], args[1], args[2]);
    }

}
