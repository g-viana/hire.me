package url.shortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import url.shortener.exception.AliasDoesNotExistException;
import url.shortener.model.SuccessResponse;
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
	public ResponseEntity<SuccessResponse> shortenUrl(@RequestParam String url, @RequestParam(required=false) String alias) {

		Url newUrl = urlService.put(url, alias);
		SuccessResponse response = new SuccessResponse(newUrl);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@RequestMapping(value = "/u/{alias}", method = RequestMethod.GET)
	public ResponseEntity<SuccessResponse> retrieve(@PathVariable String alias) {
		
		Url url = urlService.retrieve(alias);
		
		if (url == null) {
			throw new AliasDoesNotExistException(alias);
		}
		
		SuccessResponse response = new SuccessResponse(url);
		
		return ResponseEntity.ok(response);
	}
}
