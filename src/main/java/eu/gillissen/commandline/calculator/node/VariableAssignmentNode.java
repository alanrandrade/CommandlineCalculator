package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.memory.Memory;
import eu.gillissen.commandline.calculator.memory.Variable;
import eu.gillissen.commandline.calculator.tokenizer.Token;

import java.math.BigDecimal;

public class VariableAssignmentNode extends BaseNode {

    private Token literal;
    private BaseNode expression;
    private Memory memory;

    public VariableAssignmentNode(Token literal, BaseNode expression, Memory memory) {
        this.literal = literal;
        this.expression = expression;
        this.memory = memory;
    }

    @Override
    public BaseNode clone() {
        return new VariableAssignmentNode(literal, expression, memory);
    }

    @Override
    public String toString() {
        return toString(literal, expression);
    }

    @Override
    public BigDecimal evaluate() throws EvaluationException {
        if(Calc.VERBOSE) {
            System.out.println(toString());
        }
        return evaluate(new BigDecimal[]{});
    }

    @Override
    protected String toString(Object... args) {
        return String.format("%s=%s", args[0], args[1]);
    }

    @Override
    protected BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        memory.addVariable(new Variable(literal.toString(), expression.evaluate()));
        return BigDecimal.ZERO;
    }

}
