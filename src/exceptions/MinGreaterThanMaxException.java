package exceptions;

public class MinGreaterThanMaxException extends Exception {
	
	/**
	 * This exception is thrown when the minimum population value is greater than the maximum population value.
	 */
	private static final long serialVersionUID = 1L;

	public MinGreaterThanMaxException(String errorMessage) {
		super(errorMessage);
	}
}
