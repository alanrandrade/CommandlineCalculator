package eu.gillissen.commandline.calculator.exception;

public class MemoryException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MemoryException(String identifier) {
        super(String.format("Cannot find identifier '%s' in memory", identifier));
    }

}
