package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.NumberFormatter;
import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

public abstract class ExpressionNode extends BaseNode {

    public ExpressionNode(BaseNode expression) {
        children.add(expression);
    }

    protected BaseNode expression() {
        return children.get(0);
    }

    @Override
    public BigDecimal evaluate() throws EvaluationException {
        if(! isEvaluated) {
            BigDecimal expressionValue = expression().evaluate();
            value = evaluate(expressionValue);
            if(Calc.VERBOSE) {
                NumberFormatter f = NumberFormatter.getInstance();
                System.out.printf("%s = %s\n", toString(f.format(expressionValue)), f.format(value));
            }
            isEvaluated = true;
        }
        return value;
    }

    @Override
    public String toString() {
        return toString(expression());
    }

}
