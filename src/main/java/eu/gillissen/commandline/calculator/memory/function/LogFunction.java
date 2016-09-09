package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;

public class LogFunction extends Function {

    @Override
    public String getName() {
        return "log";
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        checkArguments(args.length);
        return BigDecimal.valueOf(Math.log10(Double.valueOf(args[0].toString())));//TODO maak mooi
    }

    @Override
    protected int getNumberOfArguments() {
        return 1;
    }

}
