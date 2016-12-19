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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;


public class CalcTestClass {

    private static final String allowedNameCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String[] builtinNames = {"e", "pi", "floor", "ceil", "round", "log", "ln", "sqrt", "root"};
    private Calc calc;
    private ExpressionGenerator generator;
    private Map<String, Integer> variableMemory;
    private Map<String, Integer> functionMemory;
    private Random random;
    private Requirements requirements = new Requirements();

    private enum CalcReq {
        FUNCTION_DEFINITION, VARIABLE_DEFINITION, FUNCTION_APPL_SUCCES, VARIABLE_APPL_SUCCES, VARIABLE_APPL_FAIL,
        FUNCTION_APPL_FAIL, DIV_ZERO, FUNCTION_ROUND, FUNCTION_FLOOR, FUNCTION_CEIL, VARIABLE_PI, VARIABLE_E, EXPRESSION_EQUALITY, FUNCTION_SQRT, FUNCTION_LN, FUNCTION_LOG, EXPRESSION
    }

    @BeforeSuite
    public void init() {
        generator = new ExpressionGenerator();
        random = new Random();
        Arrays.stream(CalcReq.values()).forEach(requirements::add);
    }

    @BeforeTest
    public void startTest() {
        System.out.printf("TEST START, Testing coverage %d/%d requirements\n", requirements.getRequirements().size() - requirements.getMissingCoverage().size(), requirements.getRequirements().size());
        calc = new Calc();
        variableMemory = new HashMap<>();
        functionMemory = new HashMap<>();
    }

    @AfterTest
    public void endTest() {
        System.out.println("TEST END");
        if (requirements.getMissingCoverage().isEmpty()) {
            System.out.printf("EVERYTING COVERED!! (%d requirements)\n", requirements.getRequirements().size());
        } else {
            System.out.printf("MISSING COVERAGE: %s (%d/%d)\n", requirements.getMissingCoverage(), requirements.getMissingCoverage().size(), requirements.getRequirements().size());
        }
    }

    @TestStep("round function")
    public void testRound() throws ParseException, MemoryException, EvaluationException {
        double testValue = random.nextDouble();
        BigDecimal result = calc.parse(String.format("round(%f)", testValue)).evaluate();
        int expected = (int) Math.round(testValue);
        assertZeroOrOne(result, expected);
        requirements.covered(CalcReq.FUNCTION_ROUND);
    }

    private void assertZeroOrOne(BigDecimal result, int expected) {
        if (expected == 1) {
            assertEquals(BigDecimal.ONE, result);
        } else {
            assertEquals(BigDecimal.ZERO, result);
        }
    }

    @TestStep("floor function")
    public void testFloor() throws ParseException, MemoryException, EvaluationException {
        double testValue = random.nextDouble();
        BigDecimal result = calc.parse(String.format("floor(%f)", testValue)).evaluate();
        int expected = (int) Math.floor(testValue);
        assertZeroOrOne(result, expected);
        requirements.covered(CalcReq.FUNCTION_FLOOR);
    }

    @TestStep("ceil function")
    public void testCeil() throws ParseException, MemoryException, EvaluationException {
        double testValue = random.nextDouble();
        BigDecimal result = calc.parse(String.format("ceil(%f)", testValue)).evaluate();
        int expected = (int) Math.ceil(testValue);
        assertZeroOrOne(result, expected);
        requirements.covered(CalcReq.FUNCTION_CEIL);
    }

    @TestStep("expression")
    public void testExpression() throws ParseException, MemoryException, EvaluationException {
        Expression expression = generator.generateSimpleExpression();
        assertValueExpression(expression.eval(), expression.toString());
        requirements.covered(CalcReq.EXPRESSION);
    }

    @TestStep("define function")
    public void defineFunction() throws ParseException, MemoryException, EvaluationException {
        defineInMemory(functionMemory, "%s():=%s");
        requirements.covered(CalcReq.FUNCTION_DEFINITION);
    }

    @TestStep("define variable")
    public void defineVariable() throws ParseException, MemoryException, EvaluationException {
        defineInMemory(variableMemory, "%s=%s");
        requirements.covered(CalcReq.VARIABLE_DEFINITION);
    }

    @TestStep("sqrt function")
    public void testSqrt() throws ParseException, MemoryException, EvaluationException {
        double expected = random.nextDouble();
        double testValue = Math.pow(expected, 2.0);
        assertValueExpression(expected, String.format("sqrt(%f)", testValue));
        requirements.covered(CalcReq.FUNCTION_SQRT);
    }

    @TestStep("ln function")
    public void testLn() throws ParseException, MemoryException, EvaluationException {
        double testValue = random.nextDouble();
        double expected = Math.log(testValue);
        assertValueExpression(expected, String.format("ln(%f)", testValue));
        requirements.covered(CalcReq.FUNCTION_LN);
    }

    @TestStep("log function")
    public void testLog() throws ParseException, MemoryException, EvaluationException {
        double testValue = random.nextDouble();
        double expected = Math.log10(testValue);
        assertValueExpression(expected, String.format("log(%f)", testValue));
        requirements.covered(CalcReq.FUNCTION_LOG);
    }

    private void defineInMemory(Map<String, Integer> memory, String definitionFormat) throws ParseException, MemoryException, EvaluationException {
        Expression expression = generator.generateSimpleExpression();
        String exp = expression.toString();
        int value = expression.eval();
        String name = getRandomNameNotBuiltin();
        memory.put(name, value);
        calc.parse(String.format(definitionFormat, name, exp)).evaluate();
    }

    private String getRandomNameNotBuiltin() {
        String name;
        do {
            name = getRandomName(1, 8);
        } while (isBuiltin(name));
        return name;
    }

    private boolean isBuiltin(String name) {
        return Arrays.stream(builtinNames).anyMatch(name::equals);
    }

    @TestStep("apply function")
    public void applyFunction() throws ParseException, MemoryException, EvaluationException {
        if (applyFromMemory(functionMemory, "%s()")) {
            requirements.covered(CalcReq.FUNCTION_APPL_SUCCES);
        } else {
            requirements.covered(CalcReq.FUNCTION_APPL_FAIL);
        }
    }

    @TestStep("apply variable")
    public void applyVariable() throws ParseException, MemoryException, EvaluationException {
        if (applyFromMemory(variableMemory, "%s")) {
            requirements.covered(CalcReq.VARIABLE_APPL_SUCCES);
        } else {
            requirements.covered(CalcReq.VARIABLE_APPL_FAIL);
        }
    }

    @TestStep("pi")
    public void testPi() throws ParseException, MemoryException, EvaluationException {
        BigDecimal result = calc.parse("pi").evaluate();
        assertEquals(BigDecimal.valueOf(Math.PI), result);
        requirements.covered(CalcReq.VARIABLE_PI);
    }

    @TestStep("euler")
    public void testEuler() throws ParseException, MemoryException, EvaluationException {
        BigDecimal result = calc.parse("e").evaluate();
        assertEquals(BigDecimal.valueOf(Math.E), result);
        requirements.covered(CalcReq.VARIABLE_E);
    }

    @TestStep("equality")
    public void testEquality() throws ParseException, MemoryException, EvaluationException {
        Expression left = generator.generateSimpleExpression();
        Expression right = generator.generateSimpleExpression();
        Integer leftResult = left.eval();
        Integer rightResult = right.eval();
        int expected = leftResult.compareTo(rightResult);
        assertValueExpression(expected, String.format("%s = %s", left.toString(), right.toString()));
        requirements.covered(CalcReq.EXPRESSION_EQUALITY);
    }

    private boolean applyFromMemory(Map<String, Integer> memory, String applicationFormat) throws ParseException, MemoryException, EvaluationException {
        String name = getRandomNameNotBuiltin();
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
            fail("no exception thrown");
        } catch (MemoryException e) {
            assertEquals(String.format("Cannot find identifier '%s' in memory", identifier), e.getMessage());
        }
    }

    private void assertValueExpression(double value, String expression) throws ParseException, MemoryException, EvaluationException {
        BigDecimal result = calc.parse(expression).evaluate();
        if (result.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) < 0 && result.compareTo(BigDecimal.valueOf(Double.MIN_VALUE)) > 0) {
            assertEquals(value, result.doubleValue(), 0.0005);
        } else {
            System.out.println("====== Double overflow, unable to assert ========");
        }
    }

    private void assertValueExpression(int value, String expression) throws ParseException, MemoryException, EvaluationException {
        BigDecimal result = calc.parse(expression).evaluate();
        if (result.compareTo(BigDecimal.valueOf(Integer.MAX_VALUE)) < 0 && result.compareTo(BigDecimal.valueOf(Integer.MIN_VALUE)) > 0) {
            assertEquals(value, result.intValueExact());
        } else {
            System.out.println("====== Integer overflow, unable to assert ========");
        }
    }

    @TestStep("division by zero")
    public void divideByZero() throws ParseException, MemoryException, EvaluationException {
        try {
            calc.parse("1/0").evaluate();
            fail("no exception thrown");
        } catch (ArithmeticException e) {
            if (e.getMessage() == null) {
                return;
            }
            assertEquals("/ by zero", e.getMessage());
            requirements.covered(CalcReq.DIV_ZERO);
        }
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
