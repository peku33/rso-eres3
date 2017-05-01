package pl.edu.pw.elka.rso.eres3.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(final HttpServletRequest request, 
										final HttpServletResponse response,
										final AuthenticationException exception) 
												throws IOException, ServletException 
	{
		response.getWriter().append("Bad credentials");
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
