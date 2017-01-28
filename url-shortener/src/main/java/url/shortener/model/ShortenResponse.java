package url.shortener.model;

public class ShortenResponse {

	private String alias;
	private String fullUrl;
	
	public ShortenResponse() {}
	
	public ShortenResponse(Url url) {
		this.fullUrl = url.getFullUrl();
		this.alias = url.getAlias();
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getFullUrl() {
		return fullUrl;
	}
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	
}
