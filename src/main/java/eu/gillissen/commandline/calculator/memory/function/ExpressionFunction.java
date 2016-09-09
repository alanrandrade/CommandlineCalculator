package eu.gillissen.commandline.calculator.memory.function;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.memory.Variable;
import eu.gillissen.commandline.calculator.node.BaseNode;

import java.math.BigDecimal;
import java.util.List;

public class ExpressionFunction extends Function {

    private String name;
    private BaseNode expression;

    private List<Variable> variables;

    public ExpressionFunction(String name, BaseNode expression, List<Variable> variables) {
        this.name = name;
        this.expression = expression;
        this.variables = variables;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPrintableName() {
        StringBuilder printableName = new StringBuilder();
        printableName.append(name);
        printableName.append('(');
        for(Variable var : variables) {
            printableName.append(' ');
            printableName.append(var.getName());
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
        return printableName.toString();
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        expression.setEvaluated(false);
        checkArguments(args.length);
        for(int i = 0; i < getNumberOfArguments(); i++) {
            variables.get(i).setValue(args[i]);
        }
        return expression.evaluate();
    }

    public BaseNode getExpression() {
        return expression;
    }

    public void setExpression(BaseNode expression) {
        this.expression = expression;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public int getNumberOfArguments() {
        return variables.size();
    }
}
