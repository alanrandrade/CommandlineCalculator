package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.NumberFormatter;
import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.memory.Variable;

import java.math.BigDecimal;

public class VariableNode extends BaseNode {

    private Variable variable;

    public VariableNode(Variable variable) {
        this.variable = variable;
    }

    @Override
    public BaseNode clone() {
        return new VariableNode(variable);
    }

    @Override
    public String toString() {
        return toString(variable.getName());
    }

    @Override
    public BigDecimal evaluate() throws EvaluationException {
        BigDecimal value = variable.getValue();
        if(Calc.VERBOSE) {
            NumberFormatter f = NumberFormatter.getInstance();
            System.out.printf("%s = %s\n", variable.getName(), f.format(value));
        }
        return value;
    }

    @Override
    protected String toString(Object... args) {
        return args[0].toString();
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        return args[0];
    }

    public Variable getVariable() {
        return variable;
    }

}
