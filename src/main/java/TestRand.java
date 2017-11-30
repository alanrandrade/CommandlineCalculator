import java.math.BigDecimal;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.NumberFormatter;
import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.exception.MemoryException;
import eu.gillissen.commandline.calculator.exception.ParseException;

public class TestRand {
	public static void main(String[] args) throws EvaluationException, ParseException, MemoryException {
		Calc c = new Calc();
		BigDecimal b = c.parse(String.format("7.5021427002 * 0.1332952517649845")).evaluate();
		System.out.println(Integer.MIN_VALUE);
		System.out.println(b);
		NumberFormatter f = NumberFormatter.getInstance();
		System.out.println(f.format(b));
		//System.out.println(b);
	}
}
