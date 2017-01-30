(function(){
	'use strict';
	
	//info about the url shortener service
	var config = {
		put: {
			url: "http://localhost:8080",
			method: "PUT"
		},
		retrieve: {
			url: "http://localhost:8080/",
			method: "GET"
		},
		mostAccessed: {
			url: "http://localhost:8080/most-accessed",
			method: "GET"
		}
	}
	
	//shortcut to get elements by id
	function $(id){
		return document.getElementById(id);
	}
	
	var urlInput = $("url");
	var aliasInput = $("alias");
	var messageDiv = $("resposta");
	
	function animate() {
		messageDiv.classList.remove("fadeIn");
		messageDiv.classList.add("fadeIn");
	}
	
	//handle response when shortening url
	function handlePutResponse() {
		
		if (this.readyState === 4) {
			
			var resp = JSON.parse(this.responseText);
			
			if (this.status === 201) {
				messageDiv.innerHTML = "Resultado:\r\n" + 
									   "URL original: " + resp.fullUrl + "\r\n" +
									   "URL curta: " + config.retrieve.url + resp.alias + "\r\n" +
									   "Tempo: " + resp.statistics.timeTaken + "ms";
				animate();
			} else {
				messageDiv.innerHTML = "Ocorreu um erro:\r\n" +
									   resp.description;
			}
			
		}
		
	}
	
	//function that shortens the url and show the result to the user
	$("btn-encurtar").addEventListener("click", function(){
		clearRedirect();
		var url = urlInput.value;

		if (!url) {
			messageDiv.innerHTML = "Você deve digitar a url a ser encurtada!";
			return;
		} else {
			messageDiv.innerHTML = "";
		}
		
		var req = new XMLHttpRequest();
		req.onreadystatechange = handlePutResponse;
		
		var reqUrl = config.put.url + "?url=" + url;
		
		var alias = aliasInput.value;
		if (alias) {
			reqUrl += "&alias=" + alias;
		}
		
		req.open(config.put.method, reqUrl, true);
		req.send();
		
	});
	
	//handle response from most accessed url
	function handleMostAccessedResponse() {
		
		if (this.readyState === 4) {
			
			var resp = JSON.parse(this.responseText);
			
			if (this.status === 200) {
				var table = "";
				for (var i = 0; i < resp.length; i ++) {
					var url = resp[i];
					table += '<tr>';
					table += '	<td class="mdl-data-table__cell--non-numeric">' + url.alias + '</td>';
					table += '	<td class="mdl-data-table__cell--non-numeric">' + url.fullUrl + '</td>';
					table += '	<td>' + url.numberOfUses + '</td>';
					table += '</tr>';
				}
				
				$("mais-acessadas").innerHTML = table;
			}
			
		}
	}
	
	function getMostAcessed(){
		clearRedirect();
		var req = new XMLHttpRequest();
		req.onreadystatechange = handleMostAccessedResponse;
		req.open(config.mostAccessed.method, config.mostAccessed.url, true);
		req.send();
	}
	
	getMostAcessed();
	
	//route config
	var router = new Navigo("", true);
	
	//do nothing on home page
	router.on(function(){}).resolve();  
	
	var redirect;
	
	function clearRedirect() {
		if (redirect) {
			window.clearInterval(redirect);
		}
	}
	
	function handleRetrieveResponse() {
		
		if (this.readyState === 4) {
			
			var resp = JSON.parse(this.responseText);
			
			if (this.status === 200) {
				messageDiv.innerHTML = "Redirecionando em <span id='timer'>5</span> segundo(s)...<br><br>" +
									   "URL recuperada:\r\n" +
									   "Alias: "+ resp.alias + "\r\n" +
									   "URL Original: " + resp.fullUrl;
				var counter = 5;
				var timer = $("timer");
				redirect = setInterval(function() {
					timer.innerHTML = --counter;
					if (counter === 0) {
						window.location.href = resp.fullUrl;
						clearRedirect();
					}
					
				}, 1000);
				
				
			} else {
				messageDiv.innerHTML = "Ocorreu um erro:\r\n" +
				   					   resp.description;
			}
		}
	}
	
	function retrieve(alias) {
		var req = new XMLHttpRequest();
		req.onreadystatechange = handleRetrieveResponse;
		req.open(config.retrieve.method, config.retrieve.url + alias, true);
		req.send();
	}
	
	router.on(':alias', function(params) {
		retrieve(params.alias);
	}).resolve();
})();