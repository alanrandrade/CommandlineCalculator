package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.exception.InvalidNumberOfArgumentsException;

import java.math.BigDecimal;

public abstract class Function {

    static final char[] variableNames = {'x', 'y', 'z', 'n', 'm', 'a', 'b', 'c'};

    public abstract String getName();

    public abstract BigDecimal evaluate(BigDecimal... args) throws EvaluationException;

    public String getPrintableName() {
        StringBuilder printableName = new StringBuilder();
        printableName.append(getName());
        printableName.append('(');
        for(int i = 0; i < getNumberOfArguments(); i++) {
            printableName.append(' ');
            printableName.append(variableNames[i]);
            printableName.append(',');
        }
        int index = printableName.lastIndexOf(",");
        if(index > 0) {
            printableName.deleteCharAt(index);
        }
        index = printableName.indexOf(" ");
        if(index > 0) {
            printableName.deleteCharAt(index);
        }
        printableName.append(')');
        return printableName.toString().trim();
    }

    public void checkArguments(int arguments) throws InvalidNumberOfArgumentsException {
        if(arguments != getNumberOfArguments()) {
            throw new InvalidNumberOfArgumentsException(getNumberOfArguments(), arguments);
        }
    }

    protected abstract int getNumberOfArguments();
}
