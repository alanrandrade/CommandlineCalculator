package eu.gillissen.commandline.calculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberFormatter {

    private static NumberFormatter instance;
    private DecimalFormat decimalFormat;

    private NumberFormatter() {
        decimalFormat = new DecimalFormat("0.##############");
    }

    public static NumberFormatter getInstance() {
        if(instance == null) {
            instance = new NumberFormatter();
        }
        return instance;
    }

    public String format(BigDecimal number) {
        return number.toEngineeringString();
    }

    public String[] format(BigDecimal[] numberArray) {
        String[] result = new String[numberArray.length];
        for(int i = 0; i < result.length; i++) {
            result[i] = format(numberArray[i]);
        }
        return result;
    }
}
