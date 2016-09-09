package eu.gillissen.commandline.calculator.exception;

public class EvaluationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EvaluationException(String evaluation) {
        super("Unable to evaluate: '" + evaluation + "'");
    }

}
