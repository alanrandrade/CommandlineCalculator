package eu.gillissen.commandline.calculator.memory;

import java.math.BigDecimal;

public class Variable {

    private BigDecimal value;
    private String name;

    public Variable(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String toString() {
        return getName();
    }
}
