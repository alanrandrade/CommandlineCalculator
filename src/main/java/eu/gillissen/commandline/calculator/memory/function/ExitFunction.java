package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

/**
 * @author Sasja Gillissen
 */
public class ExitFunction extends Function {
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    protected int getNumberOfArguments() {
        return 0;
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        checkArguments(args.length);
        return BigDecimal.ZERO;
    }
}
