package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.util.BigFunctions;

import java.math.BigDecimal;

/**
 * @author Sasja Gillissen
 */
public class SqrtFunction extends Function {
    @Override
    public String getName() {
        return "sqrt";
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        checkArguments(args.length);
        return BigFunctions.sqrt(args[0],args[0].scale());
    }

    @Override
    protected int getNumberOfArguments() {
        return 1;
    }
}
