package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.util.BigFunctions;

import java.math.BigDecimal;

/**
 * @author Sasja Gillissen
 */
public class RootFunction extends Function {
    @Override
    public String getName() {
        return "root";
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        checkArguments(args.length);
        return BigFunctions.root(args[0],args[1]);
    }

    @Override
    protected int getNumberOfArguments() {
        return 2;
    }
}
