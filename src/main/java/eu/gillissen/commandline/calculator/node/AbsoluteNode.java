package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

public class AbsoluteNode extends ExpressionNode {

    public AbsoluteNode(BaseNode expression) {
        super(expression);
    }

    @Override
    public BaseNode clone() {
        return new AbsoluteNode(expression());
    }

    @Override
    protected String toString(Object... args) {
        return String.format("|%s|", args[0]);
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        return args[0].abs();
    }

}
