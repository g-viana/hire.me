package url.shortener.model;

public class SuccessResponse {

	private String alias;
	private String fullUrl;
	
	public SuccessResponse() {}
	
	public SuccessResponse(Url url) {
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
