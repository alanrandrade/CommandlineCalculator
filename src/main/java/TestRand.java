import java.math.BigDecimal;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.exception.MemoryException;
import eu.gillissen.commandline.calculator.exception.ParseException;

public class TestRand {
	public static void main(String[] args) throws EvaluationException, ParseException, MemoryException {
		Calc c = new Calc();
		BigDecimal b = c.parse(String.format("%f * 0.0", 0.0283474128321382)).evaluate();
		//System.out.println(b);
	}
}
