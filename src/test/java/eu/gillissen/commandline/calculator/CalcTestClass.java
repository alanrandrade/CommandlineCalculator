package eu.gillissen.commandline.calculator;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.exception.MemoryException;
import eu.gillissen.commandline.calculator.exception.ParseException;
import osmo.tester.annotation.AfterTest;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.BeforeTest;
import osmo.tester.annotation.TestStep;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class CalcTestClass {

    ExpressionGenerator generator = new ExpressionGenerator();
    Calc calc;


    @BeforeSuite
    public void init() {

    }

    @BeforeTest
    public void startTest() {
        System.out.println("TEST START");
        calc = new Calc();
        variableMemory = new HashMap<>();
        functionMemory = new HashMap<>();
    }

    @AfterTest
    public void endTest() {
        System.out.println("TEST END");
    }


    @TestStep("expression")
    public void sayHello() throws ParseException, MemoryException, EvaluationException {
        Expression expression = generator.generateSimpleExpression();
        String exp = expression.toString();
        int value = expression.eval();
        System.out.printf("generated: %d = %s\n", value, exp);
        BigDecimal result = calc.parse(expression.toString()).evaluate();
        System.out.printf("evaluated: %d\n", value);
        assertEquals(BigDecimal.valueOf(value), result);
    }

    @TestStep("division by zero")
    public void sayWorld() {
        try {
            calc.parse("1/0").evaluate();
        } catch (Exception e) {
            assertEquals("/ by zero", e.getMessage());
        }
    }
}
