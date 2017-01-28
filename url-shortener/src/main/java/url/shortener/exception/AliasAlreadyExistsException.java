package url.shortener.exception;

import org.springframework.http.HttpStatus;

public class AliasAlreadyExistsException extends Error{

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "Custom alias \"%s\" already exists.";
	
	private String alias;
	
	/**@param alias the alias that already exists*/
	public AliasAlreadyExistsException(String alias) {
		this.alias = alias;
	}
	
	@Override
	public int getCode() {
		return 1;
	}

	@Override
	public String getDescription() {
		return String.format(DEFAULT_MESSAGE, alias);
	}
	
	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}
}
