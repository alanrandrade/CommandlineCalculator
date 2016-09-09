package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.util.BigFunctions;

import java.math.BigDecimal;

public class LnFunction extends Function {

    @Override
    public String getName() {
        return "ln";
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        checkArguments(args.length);
        return BigFunctions.ln(args[0]);
    }

    @Override
    protected int getNumberOfArguments() {
        return 1;
    }

}
