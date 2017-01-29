(function(){
	'use strict';
	
	//info about the url shortener service
	var config = {
		put: {
			url: "http://localhost:8080/create",
			method: "GET"
		},
		retrieve: {
			url: "http://localhost:8080/u/",
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
	
	//handle response when shortening url
	function handlePutResponse() {
		
		if (this.readyState === 4) {
			
			var resp = JSON.parse(this.responseText);
			
			if (this.status === 201) {
				messageDiv.innerHTML = "Resultado:\r\n" + 
									   "URL original: " + resp.fullUrl + "\r\n" +
									   "URL curta: " + config.retrieve.url + resp.alias;
			} else {
				messageDiv.innerHTML = "Ocorreu um erro:\r\n" +
									   resp.description;
			}
			
		}
		
	}
	
	//function that shortens the url and show the result to the user
	$("btn-encurtar").addEventListener("click", function(){
		
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
				var table = "10 URLs mais acessadas:\r\n\r\n" +
			 			    '<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp">' +
			 			    '	<thead>' +
						    '		<tr>' +
						    '			<th class="mdl-data-table__cell--non-numeric">Alias</th>' +
						    '			<th class="mdl-data-table__cell--non-numeric">URL Original</th>' +
						    '			<th>Nº de acessos</th>' +
						    '		</tr>' +
						    '	</thead>'+
						    '	<tbody>';
				
				for (var i = 0; i < resp.length; i ++) {
					var url = resp[i];
					table += '<tr>';
					table += '	<td class="mdl-data-table__cell--non-numeric">' + url.alias + '</td>';
					table += '	<td class="mdl-data-table__cell--non-numeric">' + url.fullUrl + '</td>';
					table += '	<td>' + url.numberOfUses + '</td>';
					table += '</tr>';
					//(i+1) +") " + config.retrieve.url + url.alias + " (" + url.fullUrl + ") com " + url.numberOfUses + " acessos.\r\n";
				}
				
				table += "</tbody></table>";
				
				messageDiv.innerHTML = table;
			}
			
		}
	}
	
	//function that will get the most acessed urls and show then in the table
	$("btn-mais-acessadas").addEventListener("click", function(){
		var req = new XMLHttpRequest();
		req.onreadystatechange = handleMostAccessedResponse;
		req.open(config.mostAccessed.method, config.mostAccessed.url, true);
		req.send();
	});
	
	//route config
	var router = new Navigo("", true);
	
	//do nothing on home page
	router.on(function(){}).resolve();  
	
	function handleRetrieveResponse() {
		
		if (this.readyState === 4) {
			
			var resp = JSON.parse(this.responseText);
			
			if (this.status === 200) {
				messageDiv.innerHTML = "URL recuperada:\r\n" +
									   "Alias: "+ resp.alias + "\r\n" +
									   "URL Original: " + resp.fullUrl +
				   					   "<br>"+
				   					   "Redirecionando em <span id='timer'>5</span> segundo(s)...";
				var counter = 5;
				var timer = $("timer");
				setInterval(function() {
					timer.innerHTML = --counter;
					if (counter === 0) {
						window.location.href = resp.fullUrl;
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