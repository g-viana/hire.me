package url.shortener.exception;

public class AliasAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "CUSTOM ALIAS ALREADY EXISTS";
	
	public AliasAlreadyExistsException() {
		this(DEFAULT_MESSAGE);
	}
	
	public AliasAlreadyExistsException(String message) {
		super(message);
	}
	
	public AliasAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	public AliasAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
