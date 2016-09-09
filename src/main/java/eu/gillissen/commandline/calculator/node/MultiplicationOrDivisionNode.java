package eu.gillissen.commandline.calculator.node;

import java.math.BigDecimal;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.tokenizer.Token;

public class MultiplicationOrDivisionNode extends SplitNode {

    public MultiplicationOrDivisionNode(BaseNode left, Token mod, BaseNode right) {
        super(left, mod, right);
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        BigDecimal leftValue = args[0];
        BigDecimal rightValue = args[1];
        if (mod.getType() == Token.Type.Multiply) {
            return leftValue.multiply(rightValue);
        }
        if (mod.getType() == Token.Type.Divide) {
            try {
                return leftValue.divide(rightValue, BigDecimal.ROUND_UNNECESSARY);
            } catch (ArithmeticException e) {
                return leftValue.divide(rightValue, Calc.SCALE, BigDecimal.ROUND_HALF_UP);
            }
        }
        throw new EvaluationException(leftValue + mod.toString() + rightValue);
    }

    @Override
    public BaseNode clone() {
        return new MultiplicationOrDivisionNode(left(), mod, right());
    }

}
