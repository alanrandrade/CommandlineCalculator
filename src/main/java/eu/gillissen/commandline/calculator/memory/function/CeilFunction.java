package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

public class CeilFunction extends Function {

    @Override
    public String getName() {
        return "ceil";
    }

    @Override
    protected int getNumberOfArguments() {
        return 1;
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        checkArguments(args.length);
        return args[0].setScale(0, BigDecimal.ROUND_CEILING);
    }

}
