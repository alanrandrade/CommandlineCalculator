package eu.gillissen.commandline.calculator.exception;

public class InvalidNumberOfArgumentsException extends EvaluationException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidNumberOfArgumentsException(int expected, int was) {
        super(String.format("Incorrect number of arguments expected <%d> was <%d>", expected, was));
    }
}
