package pl.edu.pw.elka.rso.eres3.security.exceptions;

@SuppressWarnings("serial")
public class LoginExistsException extends Throwable {

    public LoginExistsException(final String message) {
        super(message);
    }

}