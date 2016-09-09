package eu.gillissen.commandline.calculator.node;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.tokenizer.Token;

import java.math.BigDecimal;

public class NumberNode extends BaseNode {
    private Token number;
    private Token floatingPointValue;

    public NumberNode(Token number) {
        this.number = number;
    }

    public NumberNode(Token number, Token floatingPointValue) {
        this.number = number;
        this.floatingPointValue = floatingPointValue;
    }

    @Override
    public BigDecimal evaluate() throws EvaluationException {
        if(! isEvaluated) {
            value = evaluate(new BigDecimal(toString()));
            isEvaluated = true;
        }
        return value;
    }

    @Override
    public BigDecimal evaluate(BigDecimal... args) throws EvaluationException {
        return args[0];
    }

    @Override
    public String toString() {
        return toString(number, floatingPointValue);
    }

    @Override
    public BaseNode clone() {
        if(floatingPointValue != null) {
            return new NumberNode(number, floatingPointValue);
        }
        return new NumberNode(number);
    }

    @Override
    protected String toString(Object... args) {
        if(args[1] != null) {
            return args[0] + "." + args[1];
        } else {
            return number.toString();
        }
    }
}
