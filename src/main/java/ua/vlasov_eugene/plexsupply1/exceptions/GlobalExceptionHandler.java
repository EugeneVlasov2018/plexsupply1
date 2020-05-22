package ua.vlasov_eugene.plexsupply1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = {FTPWriteException.class})
	public ResponseEntity<ErrorMessage> handleProblemWithFTP(FTPWriteException ex) {
		return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Data
	@AllArgsConstructor
	private static class ErrorMessage {
		private String exceptionMessage;
	}
}
