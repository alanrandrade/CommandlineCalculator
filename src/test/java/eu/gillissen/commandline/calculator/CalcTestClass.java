package eu.gillissen.commandline.calculator;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.exception.MemoryException;
import eu.gillissen.commandline.calculator.exception.ParseException;
import osmo.tester.annotation.AfterTest;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.BeforeTest;
import osmo.tester.annotation.TestStep;
import osmo.tester.model.Requirements;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class CalcTestClass {

    private static final String allowedNameCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Calc calc;
    private ExpressionGenerator generator;
    private Map<String, Integer> variableMemory;
    private Map<String, Integer> functionMemory;
    private Random random;
    Requirements requirements = new Requirements();

    protected enum CalcReq {
        FUNCTION_DEFINITION,
        VARIABLE_DEFINITION,
        FUNCTION_APPL_SUCCES,
        VARIABLE_APPL_SUCCES,
        VARIABLE_APPL_FAIL,
        FUNCTION_APPL_FAIL,
        DIV_ZERO,
        EXPRESSION
    }

    @BeforeSuite
    public void init() {
        generator = new ExpressionGenerator();
        random = new Random();
        requirements.add(CalcReq.FUNCTION_DEFINITION);
        requirements.add(CalcReq.VARIABLE_DEFINITION);
        requirements.add(CalcReq.FUNCTION_APPL_SUCCES);
        requirements.add(CalcReq.VARIABLE_APPL_SUCCES);
        requirements.add(CalcReq.VARIABLE_APPL_FAIL);
        requirements.add(CalcReq.FUNCTION_APPL_FAIL);
        requirements.add(CalcReq.EXPRESSION);
        requirements.add(CalcReq.DIV_ZERO);
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
        if (requirements.getMissingCoverage().isEmpty()) {
            System.out.println("EVERYTING COVERED!!");
        } else {
            System.out.println("MISSING COVERAGE: " + requirements.getMissingCoverage());
        }
    }


    @TestStep("expression")
    public void expression() throws ParseException, MemoryException, EvaluationException {
        Expression expression = generator.generateSimpleExpression();
        assertValueExpression(expression.eval(), expression.toString());
        requirements.covered(CalcReq.EXPRESSION);
    }

    @TestStep("defineFunction")
    public void defineFunction() throws ParseException, MemoryException, EvaluationException {
        defineInMemory(functionMemory, "%s():=%s");
        requirements.covered(CalcReq.FUNCTION_DEFINITION);
    }

    @TestStep("defineVariable")
    public void defineVariable() throws ParseException, MemoryException, EvaluationException {
        defineInMemory(variableMemory, "%s=%s");
        requirements.covered(CalcReq.VARIABLE_DEFINITION);
    }

    private void defineInMemory(Map<String, Integer> memory, String definitionFormat) throws ParseException, MemoryException, EvaluationException {
        Expression expression = generator.generateSimpleExpression();
        String exp = expression.toString();
        int value = expression.eval();
        String name = getFreshName();
        memory.put(name, value);
        calc.parse(String.format(definitionFormat, name, exp)).evaluate();
    }

    @TestStep("applyFunction")
    public void applyFunction() throws ParseException, MemoryException, EvaluationException {
        if (applyFromMemory(functionMemory, "%s()")) {
            requirements.covered(CalcReq.FUNCTION_APPL_SUCCES);
        } else {
            requirements.covered(CalcReq.FUNCTION_APPL_FAIL);
        }
    }

    @TestStep("applyVariable")
    public void applyVariable() throws ParseException, MemoryException, EvaluationException {
        if (applyFromMemory(variableMemory, "%s")) {
            requirements.covered(CalcReq.VARIABLE_APPL_SUCCES);
        } else {
            requirements.covered(CalcReq.VARIABLE_APPL_FAIL);
        }
    }

    private boolean applyFromMemory(Map<String, Integer> memory, String applicationFormat) throws ParseException, MemoryException, EvaluationException {
        String name = getRandomName(1, 8);
        if (memory.containsKey(name)) {
            int value = memory.get(name);
            assertValueExpression(value, String.format(applicationFormat, name));
            return true;
        } else {
            assertMemoryError(name, String.format(applicationFormat, name));
            return false;
        }
    }

    private void assertMemoryError(String identifier, String expression) throws ParseException, EvaluationException {
        try {
            calc.parse(expression).evaluate();
        } catch (MemoryException e) {
            assertEquals(String.format("Cannot find identifier '%s' in memory", identifier), e.getMessage());
        }
    }

    private void assertValueExpression(int value, String expression) throws ParseException, MemoryException, EvaluationException {
        BigDecimal result = calc.parse(expression).evaluate();
        assertEquals(BigDecimal.valueOf(value), result);
    }

    @TestStep("division by zero")
    public void divideByZero() throws ParseException, MemoryException, EvaluationException {
        try {
            calc.parse("1/0").evaluate();
        } catch (ArithmeticException e) {
            assertEquals("/ by zero", e.getMessage());
            requirements.covered(CalcReq.DIV_ZERO);
        }
    }

    public String getFreshName() {
        String name;
//        do {
        name = getRandomName(1, 8);
//        } while (!isInMemory(name));
        return name;
    }

    private boolean isInMemory(String name) {
        return variableMemory.containsKey(name) || functionMemory.containsKey(name);
    }

    public String getRandomName(int min, int max) {
        int length = min;
        assertFalse(min <= 0 || min > max);
        if (min != max) {
            length = random.nextInt(max - min) + min;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int charIndex = random.nextInt(allowedNameCharacters.length());
            builder.append(allowedNameCharacters.charAt(charIndex));
        }
        return builder.toString();
    }
}
