package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

public class FloorFunction extends Function {

    @Override
    public String getName() {
        return "floor";
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        checkArguments(args.length);
        return args[0].setScale(0, BigDecimal.ROUND_FLOOR);
    }

    @Override
    protected int getNumberOfArguments() {
        return 1;
    }

}
