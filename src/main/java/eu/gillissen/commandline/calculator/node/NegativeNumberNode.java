package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

public class NegativeNumberNode extends BaseNode {

    public NegativeNumberNode(BaseNode number) {
        children.add(number);
    }

    private BaseNode number() {
        return children.get(0);
    }

    @Override
    public BigDecimal evaluate() throws EvaluationException {
        if(! isEvaluated) {
            value = evaluate(number().evaluate());
            isEvaluated = true;
        }
        return value;
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        return args[0].negate();
    }

    @Override
    public String toString() {
        return toString(number());
    }

    @Override
    protected String toString(Object... args) {
        return "-" + args[0];
    }

    @Override
    public BaseNode clone() {
        return new NegativeNumberNode(number());
    }

}
