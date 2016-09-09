package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.tokenizer.Token;

import java.math.BigDecimal;

public class ModuloNode extends SplitNode {

    public ModuloNode(BaseNode left, Token mod, BaseNode right) {
        super(left, mod, right);
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        BigDecimal expressionValue = args[0];
        BigDecimal moduloValue = args[1];
        return expressionValue.remainder(moduloValue);
    }

    @Override
    public BaseNode clone() {
        return new ModuloNode(left(), mod, right());
    }

    protected String toString(Object... args) {
        return String.format("%s%s%s", args[0], args[1], args[2]);
    }

}
