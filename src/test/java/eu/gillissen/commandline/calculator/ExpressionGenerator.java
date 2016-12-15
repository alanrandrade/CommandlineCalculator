package eu.gillissen.commandline.calculator;

import java.util.Random;

/**
 * @author Sasja Gillissen
 */
public class ExpressionGenerator {

    private Random random = new Random();

    public Expression generateSimpleExpression() {
        return generateExpression(5);
    }

    private Expression generateExpression(int depth) {
        if (depth <= 0) {
            return new Expression(randomValue());
        }
        return randomExpression(generateExpression(depth - 1), generateExpression(depth - 1));
    }

    private Expression randomExpression(Expression left, Expression right) {
        ExpressionType type = randomExpressionType();
        if (type == ExpressionType.VALUE) {
            return new Expression(randomValue());
        } else {
            return new Expression(type, left, right);
        }
    }

    private int randomValue() {
        return random.nextInt(10);
    }

    private ExpressionType randomExpressionType() {
        ExpressionType[] expressionTypes = ExpressionType.values();
        return expressionTypes[random.nextInt(expressionTypes.length)];
    }

}
