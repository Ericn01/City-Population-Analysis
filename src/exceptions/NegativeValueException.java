package exceptions;

public class NegativeValueException extends Exception{

	/**
	 * This exception is thrown when a population input has a value that is less than 0.
	 */
	private static final long serialVersionUID = 1L;

	public NegativeValueException(String errorMessage) {
		super(errorMessage);
	}
	
	
}
