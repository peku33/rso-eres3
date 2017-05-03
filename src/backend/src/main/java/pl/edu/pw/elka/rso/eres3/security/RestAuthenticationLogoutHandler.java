package pl.edu.pw.elka.rso.eres3.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class RestAuthenticationLogoutHandler extends SimpleUrlLogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final Authentication authentication) 
					throws IOException 
	{
		response.getWriter().append("OK");
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
