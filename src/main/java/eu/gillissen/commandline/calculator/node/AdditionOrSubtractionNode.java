package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.tokenizer.Token;

import java.math.BigDecimal;

public class AdditionOrSubtractionNode extends SplitNode {

    public AdditionOrSubtractionNode(BaseNode left, Token mod, BaseNode right) {
        super(left, mod, right);
    }

    @Override
    public BaseNode clone() {
        return new AdditionOrSubtractionNode(left(), mod, right());
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        BigDecimal leftValue = args[0];
        BigDecimal rightValue = args[1];
        if(mod.getType() == Token.Type.Add)
            return leftValue.add(rightValue);
        if(mod.getType() == Token.Type.Subtract)
            return leftValue.subtract(rightValue);
        throw new EvaluationException(leftValue + mod.toString() + rightValue);
    }

}
