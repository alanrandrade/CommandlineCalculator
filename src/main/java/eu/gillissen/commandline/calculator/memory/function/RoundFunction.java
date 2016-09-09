package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

public class RoundFunction extends Function {

    @Override
    public String getName() {
        return "round";
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        checkArguments(args.length);
        return args[0].setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    protected int getNumberOfArguments() {
        return 1;
    }

}
