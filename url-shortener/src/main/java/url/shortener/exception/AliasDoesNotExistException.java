package url.shortener.exception;

public class AliasDoesNotExistException extends Error{

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "Alias \"%s\" does not exist.";
	
	private String alias;
	
	public AliasDoesNotExistException(String alias) {
		this.alias = alias;
	}
	
	@Override
	public int getCode() {
		return 2;
	}

	@Override
	public String getDescription() {
		return String.format(DEFAULT_MESSAGE, alias);
	}

}
