package url.shortener.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import url.shortener.exception.AliasDoesNotExistException;
import url.shortener.model.Url;
import url.shortener.model.UrlAndStatistics;
import url.shortener.model.UrlView;
import url.shortener.service.UrlService;

@RestController @RequestMapping("/")
public class UrlController {

	private UrlService urlService;
	
	@Autowired
	public UrlController(UrlService urlService) {
		this.urlService = urlService;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@JsonView(UrlView.UrlAndStatistics.class)
	public ResponseEntity<UrlAndStatistics> shortenUrl(@RequestParam String url, @RequestParam(required=false) String alias) {
		long start = System.currentTimeMillis();
		
		Url newUrl = urlService.put(url, alias);
		
		long timeTaken = System.currentTimeMillis() - start;
		
		UrlAndStatistics response = new UrlAndStatistics(newUrl);
		response.getStatistics().setTimeTaken(timeTaken);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@RequestMapping(value = "{alias}", method = RequestMethod.GET)
	@JsonView(UrlView.Simple.class)
	public ResponseEntity<Url> retrieveUrl(@PathVariable String alias) {
		
		Url url = urlService.retrieve(alias);
		
		if (url == null) {
			throw new AliasDoesNotExistException(alias);
		}
		
		return ResponseEntity.ok(url);
	}
	
	@RequestMapping(value = "/most-accessed", method = RequestMethod.GET)
	@JsonView(UrlView.MostAccessed.class)
	public ResponseEntity<List<Url>> getMostAccessed() {
		List<Url> mostAccessed = urlService.getMostAccessed();
		return ResponseEntity.ok(mostAccessed);
	}
}
