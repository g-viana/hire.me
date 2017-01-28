package url.shortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import url.shortener.model.ShortenResponse;
import url.shortener.model.Url;
import url.shortener.service.UrlService;

@RestController
public class UrlController {

	private UrlService urlService;
	
	@Autowired
	public UrlController(UrlService urlService) {
		this.urlService = urlService;
	}
	
	@RequestMapping(value = "/create", method = { RequestMethod.GET, RequestMethod.PUT })
	public ResponseEntity<Object> shortenUrl(@RequestParam String url, @RequestParam(required=false) String alias) {

		Url newUrl = urlService.put(url, alias);
		ShortenResponse response = new ShortenResponse(newUrl);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@RequestMapping(value = "/u/{alias}", method = RequestMethod.GET)
	public ResponseEntity<Object> getFullUrl(@PathVariable String alias) {
		
		@SuppressWarnings("unused")
		Url url = urlService.retrieve(alias);
		
		
		return null;
	}
}
