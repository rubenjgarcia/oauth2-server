package es.rubenjgarcia.oauth2.server.controller.error;

import es.rubenjgarcia.oauth2.server.controller.error.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
public class ErrorController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ErrorResponse(messageSource.getMessage("error.usernameNotFoundError", null, Locale.ENGLISH)); // FIXME
    }

    @ExceptionHandler(ClientRegistrationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleClientRegistrationException(ClientRegistrationException ex) {
        return new ErrorResponse(messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), Locale.ENGLISH)); // FIXME
    }

    @ExceptionHandler(NoSuchClientException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleNoSuchClientException(NoSuchClientException ex) {
        return new ErrorResponse(messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), Locale.ENGLISH)); // FIXME
    }
}
