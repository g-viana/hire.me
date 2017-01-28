package url.shortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import url.shortener.exception.AliasAlreadyExistsException;
import url.shortener.model.Url;
import url.shortener.repository.UrlRepo;

@Service
public class UrlService {

	private UrlRepo repo;
	
	@Autowired
	public UrlService(UrlRepo repo) {
		this.repo = repo;
	}
	
	@Transactional
	public Url put(String fullUrl, String customAlias) {
		
		Url url;
		//see if it already exists:
		url = repo.findWithCustomAlias(customAlias);
		if (url != null) {
			throw new AliasAlreadyExistsException();
		}
		
		//creates a new one:
		url = new Url();
		url.setFullUrl(fullUrl);
		
		repo.save(url);
		
		if (customAlias == null) {
			url.setShortUrl(ShortURL.encode(url.getId().intValue()));
		} else {
			url.setShortUrl(customAlias);
			url.setCustom(true);
		}
		
		repo.save(url);
		
		return url;
	}
	
	public Url retrieve() {
		return null;
	}
}
