package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.memory.Memory;
import eu.gillissen.commandline.calculator.memory.Variable;
import eu.gillissen.commandline.calculator.memory.function.ExpressionFunction;
import eu.gillissen.commandline.calculator.tokenizer.Token;

import java.math.BigDecimal;
import java.util.List;

public class FunctionAssignmentNode extends BaseNode {

    private Token literal;
    private BaseNode expression;
    private Memory memory;
    private List<Variable> variables;

    public FunctionAssignmentNode(Token literal, BaseNode expression, List<Variable> variables,
                                  Memory memory) {
        this.literal = literal;
        this.expression = expression;
        this.variables = variables;
        this.memory = memory;
    }

    @Override
    public BaseNode clone() {
        return new FunctionAssignmentNode(literal, expression, variables, memory);
    }

    @Override
    public String toString() {
        return toString(literal, variables.toArray(), expression);
    }

    @Override
    public BigDecimal evaluate() throws EvaluationException {
        evaluate(new BigDecimal[]{});
        if(Calc.VERBOSE) {
            System.out.println(toString());
        }
        return BigDecimal.ZERO;
    }

    @Override
    protected String toString(Object... args) {
        String name = args[0].toString();
        Object[] arguments = (Object[]) args[1];
        String expression = args[2].toString();
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
        return String.format("%s = %s", string.toString(), expression);
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        memory.addFunction(new ExpressionFunction(literal.toString(), expression, variables));
        return BigDecimal.ZERO;
    }

}
