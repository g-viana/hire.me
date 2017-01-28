package url.shortener.exception;

import org.springframework.http.HttpStatus;

/** Abstract class to represent errors. Subclasses will be automagically be
 * caught by the {@link GlobalExceptionHandler} and transformed into a response for the client.
 * <br><br>
 * Subclasses must override the {@link #getCode()} and {@link #getDescription()} methods.
 * They may also override the {@link #getHttpStatus()} method.
 * */
public abstract class Error extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/** Returns this error's code.*/
	public abstract int getCode();
	
	/** Returns a description of this error*/
	public abstract String getDescription();
	
	/** HttpStatus for this error. By default, it is {@code HttpStatus.UNPROCESSABLE_ENTITY}*/
	public HttpStatus getHttpStatus(){
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}
	
	@Override
	public String getMessage() {
		return getDescription();
	}
}
