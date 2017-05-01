package pl.edu.pw.elka.rso.eres3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * Added for testing purposes
	 * @todo: Both should be enabled on production
	 */
	static final boolean REQUIRE_AUTHENTICATION = true;
	static final boolean ENABLE_CSRF_PROTECTION = false;
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/**
		 * Config for authentication and CSRF tokens handling. 
		 */
		if(REQUIRE_AUTHENTICATION)
		{
			http.authorizeRequests()
				.anyRequest().authenticated();
		} else
		{
			http.authorizeRequests()
				.anyRequest().permitAll();
		}
		http.formLogin()
			.usernameParameter("username") /* These might be just a default, dk */
			.passwordParameter("password")
			.loginPage("/login")
			.successHandler(new RestAuthenticationSuccessHandler())
			.failureHandler(new RestAuthenticationFailureHandler())
			.permitAll()
		.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessHandler(new RestAuthenticationLogoutHandler())
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
		.and()
			.exceptionHandling()
			.authenticationEntryPoint(new RestAuthenticationEntryPoint());
		
		if(ENABLE_CSRF_PROTECTION)
		{
			http.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
            .csrf().csrfTokenRepository(csrfTokenRepository());
		} else
		{
			http.csrf().disable();
		}
	}

	private CsrfTokenRepository csrfTokenRepository() {
		/**
		 * Angular sends CSRF token in X-XSRF-TOKEN header instead of X-CSRF-TOKEN
		 */
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
