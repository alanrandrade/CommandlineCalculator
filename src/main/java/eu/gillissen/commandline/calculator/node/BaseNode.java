package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.exception.EvaluationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseNode implements Cloneable {

    protected boolean isEvaluated = false;

    protected BigDecimal value = BigDecimal.ZERO;

    protected List<BaseNode> children;

    public BaseNode() {
        children = new ArrayList<BaseNode>();
    }

    @Override
    public abstract BaseNode clone();

    @Override
    public abstract String toString();

    public abstract BigDecimal evaluate() throws EvaluationException;

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public void setEvaluated(boolean evaluated) {
        isEvaluated = evaluated;
        for(BaseNode child : children) {
            child.setEvaluated(evaluated);
        }
    }

    public List<BaseNode> getChildren() {
        return children;
    }

    protected abstract String toString(Object... args);

    protected abstract BigDecimal evaluate(BigDecimal... args) throws EvaluationException;
}
