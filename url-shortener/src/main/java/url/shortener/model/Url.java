package url.shortener.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Url {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView({UrlView.MostAccessed.class, UrlView.Simple.class})
	@Column(nullable = false)
	private String fullUrl;
	
	@JsonView({UrlView.MostAccessed.class, UrlView.Simple.class})
	@Column(unique = true)
	private String alias;
	
	private Boolean custom = Boolean.FALSE;
	
	@JsonView(UrlView.MostAccessed.class)
	private Long numberOfUses = 0L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Boolean getCustom() {
		return custom;
	}

	public void setCustom(Boolean custom) {
		this.custom = custom;
	}
	
	public Long getNumberOfUses() {
		return numberOfUses;
	}

	public void setNumberOfUses(Long numberOfUses) {
		this.numberOfUses = numberOfUses;
	}
	
}
