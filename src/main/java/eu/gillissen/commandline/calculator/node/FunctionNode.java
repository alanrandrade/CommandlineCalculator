package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.NumberFormatter;
import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.memory.function.ExpressionFunction;
import eu.gillissen.commandline.calculator.memory.function.Function;

import java.math.BigDecimal;
import java.util.List;

public class FunctionNode extends BaseNode {

    private Function function;

    public FunctionNode(Function function, List<BaseNode> arguments) {
        this.function = function;
        children = arguments;
    }

    public BigDecimal evaluate() throws EvaluationException {
        if(! isEvaluated) {
            BigDecimal argumentValues[] = getArgumentValues();
            value = function.evaluate(argumentValues);
            if(function instanceof ExpressionFunction && Calc.VERBOSE) {
                NumberFormatter f = NumberFormatter.getInstance();
                System.out.printf(String.format("%s = %s\n",
                        toString(function.getName(), f.format(argumentValues)),
                        f.format(value)));
            }
            isEvaluated = true;
        }
        return value;
    }

    private BigDecimal[] getArgumentValues() throws EvaluationException {
        BigDecimal argumentValues[] = new BigDecimal[children.size()];
        for(int i = 0; i < children.size(); i++) {
            argumentValues[i] = children.get(i).evaluate();

        }
        return argumentValues;
    }

    @Override
    public String toString() {
        return toString(function.getName(), arguments().toArray());
    }

    @Override
    protected String toString(Object... args) {
        String name = (String) args[0];
        Object[] arguments = (Object[]) args[1];
        StringBuilder string = new StringBuilder();
        string.append(name);
        string.append("(");
        if(arguments.length > 0) {
            for(int i = 0; i < arguments.length; i++) {
                string.append(arguments[i].toString());
                if(i != arguments.length - 1) {
                    string.append(", ");
                }
            }
        }
        string.append(')');
        return string.toString();
    }

    protected List<BaseNode> arguments() {
        return children;
    }

    @Override
    public BaseNode clone() {
        return new FunctionNode(function, arguments());
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        return function.evaluate(args);
    }
}
