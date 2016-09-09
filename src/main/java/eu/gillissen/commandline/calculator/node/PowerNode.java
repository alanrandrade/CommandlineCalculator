package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.tokenizer.Token;
import eu.gillissen.commandline.calculator.util.BigFunctions;

import java.math.BigDecimal;

public class PowerNode extends SplitNode {

    public PowerNode(BaseNode expression, Token mod, BaseNode power) {
        super(expression, mod, power);
    }

    @Override
    public BaseNode clone() {
        return new PowerNode(left(), mod, right());
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        try {
            return BigFunctions.power(args[0], args[1]);
        } catch(Exception e) {
            return BigFunctions.intPower(args[0],args[1].longValue());
        }
    }

    protected String toString(Object... args) {
        return String.format("%s%s%s", args[0], args[1], args[2]);
    }

}
