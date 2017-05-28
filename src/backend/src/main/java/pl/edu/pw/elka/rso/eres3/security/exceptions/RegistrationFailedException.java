package pl.edu.pw.elka.rso.eres3.security.exceptions;

@SuppressWarnings("serial")
public class RegistrationFailedException extends Throwable {

    public RegistrationFailedException(final String message) {
        super(message);
    }

}