package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

public class BracketNode extends ExpressionNode {

    public BracketNode(BaseNode expression) {
        super(expression);
    }

    @Override
    public BigDecimal evaluate() throws EvaluationException {
        if(! isEvaluated) {
            BigDecimal expressionValue = expression().evaluate();
            value = evaluate(expressionValue);
            isEvaluated = true;
        }
        return value;
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        return args[0];
    }

    @Override
    public String toString(Object... args) {
        return String.format("(%s)", args[0]);
    }

    @Override
    public BaseNode clone() {
        return new BracketNode(expression());
    }

}
