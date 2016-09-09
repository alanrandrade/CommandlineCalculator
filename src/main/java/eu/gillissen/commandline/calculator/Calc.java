package eu.gillissen.commandline.calculator;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.exception.MemoryException;
import eu.gillissen.commandline.calculator.exception.ParseException;
import eu.gillissen.commandline.calculator.memory.Memory;
import eu.gillissen.commandline.calculator.memory.Variable;
import eu.gillissen.commandline.calculator.memory.function.CeilFunction;
import eu.gillissen.commandline.calculator.memory.function.ExitFunction;
import eu.gillissen.commandline.calculator.memory.function.FloorFunction;
import eu.gillissen.commandline.calculator.memory.function.LnFunction;
import eu.gillissen.commandline.calculator.memory.function.LogFunction;
import eu.gillissen.commandline.calculator.memory.function.RootFunction;
import eu.gillissen.commandline.calculator.memory.function.RoundFunction;
import eu.gillissen.commandline.calculator.memory.function.SqrtFunction;
import eu.gillissen.commandline.calculator.node.BaseNode;
import eu.gillissen.commandline.calculator.node.EqualityNode;
import eu.gillissen.commandline.calculator.node.FunctionAssignmentNode;
import eu.gillissen.commandline.calculator.node.VariableAssignmentNode;
import eu.gillissen.commandline.calculator.tokenizer.Tokenizer;

public class Calc {

    public static final int SCALE = 16;
    public static boolean DEBUG = true;
    public static boolean VERBOSE = true;
    private static boolean running;
    private Memory memory;

    public Calc() {
        memory = new Memory();
        loadBuiltin();
    }

    public static void main(String[] args) {
        if (expressionParams(args)) {
            Calc calculator = new Calc();
            String input = "";
            for (String arg : args) {
                input += arg;
            }
            calculator.calculate(input);
        } else {
            Scanner scanner = new Scanner(System.in);
            String input;
            running = true;
            Calc calculator = new Calc();
            do {
                System.out.print("? ");
                input = scanner.nextLine();
                if (input.length() > 0) {
                    calculator.calculate(input);
                }
            } while (running);
            System.out.println("Bye!");
            scanner.close();
        }
    }

    private static boolean verboseParams(String[] args) {
        return args.length > 0 && "-v".equals(args[0]);
    }

    private static boolean expressionParams(String[] args) {
        if (verboseParams(args)) {
            return args.length > 1;
        }
        return args.length > 0;
    }

    public void loadBuiltin() {
        memory.addVariable(new Variable("pi", BigDecimal.valueOf(Math.PI)));
        memory.addVariable(new Variable("e", BigDecimal.valueOf(Math.E)));
        memory.addFunction(new FloorFunction());
        memory.addFunction(new CeilFunction());
        memory.addFunction(new RoundFunction());
        memory.addFunction(new LogFunction());
        memory.addFunction(new LnFunction());
        memory.addFunction(new SqrtFunction());
        memory.addFunction(new RootFunction());
        if (running) {
            memory.addFunction(new ExitFunction());
        }
    }

    public void calculate(String input) {
        BaseNode rootNode = null;
        try {
            rootNode = parse(input);
        } catch (ParseException | MemoryException e) {
            handleError(e);
        }
        if (rootNode != null) {
            if ("exit()".equals(rootNode.toString())) {
                running = false;
            } else {
                boolean hasResult = process(rootNode);
                evaluate(hasResult, rootNode);
            }
        }
    }

    public BaseNode parse(String input) throws ParseException, MemoryException {
        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer, memory);
        return parser.parse();
    }

    public boolean process(BaseNode rootNode) {
        boolean hasResult = true;
        if (rootNode instanceof FunctionAssignmentNode || rootNode instanceof EqualityNode
                || rootNode instanceof VariableAssignmentNode) {
            hasResult = false;
        } else {
            Simplifier.simplify(rootNode);
        }
        return hasResult;
    }

    public void evaluate(boolean hasResult, BaseNode rootNode) {
        if (VERBOSE) {
            try {
                BigDecimal result = rootNode.evaluate();
                System.out.println("Calculations:");
                if (hasResult) {
                    NumberFormatter f = NumberFormatter.getInstance();
                    System.out.printf("Result:%n%s = %s%n", rootNode.toString(), f.format(result));
                }
            } catch (EvaluationException e) {
                handleError(e);
            }
        } else {
            try {
                BigDecimal result = rootNode.evaluate();
                if (hasResult) {
                    NumberFormatter f = NumberFormatter.getInstance();
                    System.out.printf("= %s%n", f.format(result));
                }
            } catch (EvaluationException e) {
                handleError(e);
            }
        }
    }

    private void handleError(Exception e) {
        if (DEBUG) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } else {
            System.out.println(e.getMessage());
        }
    }

    public void clearMemory() {
        memory = new Memory();
    }

    public Map<String, String> getVariables() {
        return memory.getVariables();
    }

    public Map<String, String> getFunctions() {
        return memory.getFunctions();
    }
}
