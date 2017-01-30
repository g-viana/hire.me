package url.shortener.model;

import com.fasterxml.jackson.annotation.JsonView;

public class Statistics {

	@JsonView(UrlView.UrlAndStatistics.class)
	private long timeTaken;

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}
	
}
