package url.shortener.model;

import com.fasterxml.jackson.annotation.JsonView;

public class UrlAndStatistics extends Url{

	@JsonView(UrlView.UrlAndStatistics.class)
	private Statistics statistics = new Statistics();

	public UrlAndStatistics(){}
	
	public UrlAndStatistics(Url url) {
		setAlias(url.getAlias());
		setCustom(url.getCustom());
		setFullUrl(url.getFullUrl());
		setId(url.getId());
		setNumberOfUses(url.getNumberOfUses());
	}
	
	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	
	
}
