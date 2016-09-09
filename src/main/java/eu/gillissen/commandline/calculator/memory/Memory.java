package eu.gillissen.commandline.calculator.memory;

import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.memory.function.ExpressionFunction;
import eu.gillissen.commandline.calculator.memory.function.Function;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    private Map<String, Variable> variables;
    private Map<String, Function> functions;

    public Memory() {
        variables = new HashMap<String, Variable>();
        functions = new HashMap<String, Function>();
    }

    public Variable getVariable(String identifier) {
        return variables.get(identifier);
    }

    public Function getFunction(String identifier) {
        return functions.get(identifier);
    }


    public void addFunction(Function function) {
        if(functions.containsKey(function.getName())) {
            Function oldFunction = functions.get(function.getName());
            if(oldFunction instanceof ExpressionFunction && function instanceof ExpressionFunction && ((ExpressionFunction) oldFunction).getNumberOfArguments() == ((ExpressionFunction) function).getNumberOfArguments()) {
                ((ExpressionFunction) oldFunction).setExpression(((ExpressionFunction) function).getExpression());
                ((ExpressionFunction) oldFunction).setVariables(((ExpressionFunction) function).getVariables());
            } else {
                functions.replace(function.getName(), function);
            }
            if(Calc.DEBUG) {
                System.out.printf("replaced function %s in memory \n", function.getName());
            }
        } else {
            functions.put(function.getName(), function);
            if(Calc.DEBUG) {
                System.out.printf("added function %s to memory \n", function.getName());
            }
        }
    }

    public void addVariable(Variable variable) {
        if(variables.containsKey(variable.getName())) {
            variables.get(variable.getName()).setValue(variable.getValue());
            if(Calc.DEBUG) {
                System.out.printf("replaced variable %s in memory \n", variable.getName());
            }
        } else {
            variables.put(variable.getName(), variable);
            if(Calc.DEBUG) {
                System.out.printf("added variable %s to memory \n", variable.getName());
            }
        }
    }

    public Map<String, String> getVariables() {
        Map<String, String> vars = new HashMap<String, String>();
        for(Map.Entry<String, Variable> entry : variables.entrySet()) {
            vars.put(entry.getKey(), String.valueOf(entry.getValue().getValue()));
        }
        return vars;
    }

    public Map<String, String> getFunctions() {
        Map<String, String> funcs = new HashMap<String, String>();
        for(Map.Entry<String, Function> entry : functions.entrySet()) {
            String value = "";
            if(entry.getValue() instanceof ExpressionFunction) {
                value = ((ExpressionFunction) entry.getValue()).getExpression().toString();
            }
            funcs.put(entry.getValue().getPrintableName(), value);
        }
        return funcs;
    }
}
