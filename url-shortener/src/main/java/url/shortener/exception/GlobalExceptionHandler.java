package url.shortener.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import url.shortener.model.ErrorResponse;

/** Class that catches any subclass of {@link Error} and handles it, creating an appropriate response to the client*/
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Error.class)
	public ResponseEntity<ErrorResponse> handleException(Error error) {
		
		ErrorResponse response = new ErrorResponse();
		response.setDescription(error.getDescription());
		response.setErrorCode(error.getCode());
		
		return ResponseEntity.status(error.getHttpStatus()).body(response);
	}
}
