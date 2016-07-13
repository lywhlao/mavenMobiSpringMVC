package base.web.util;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "NOT FOUND!!!")
public class EmailException extends MessagingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
