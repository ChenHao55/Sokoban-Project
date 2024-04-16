package exceptions;


public class IlegalPositionException extends Exception {
	private static final long serialVersionUID = 1L;

	public IlegalPositionException(String message) {
		super(message);
	}
}
