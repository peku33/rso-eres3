package pl.edu.pw.elka.rso.eres3.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import pl.edu.pw.elka.rso.eres3.security.domain.DomainPermissionEvaluator;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(DomainPermissionEvaluator.class);

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.debug(true);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		/**
		 * Config for authentication and CSRF tokens handling.
		 */
		if(securitySettings().getRequireAuthentication())
		{
			logger.info("Authentication requirement enabled.");
			http.authorizeRequests()
				.anyRequest().authenticated();
		} else
		{
			logger.warn("Authentication requirement disabled!");
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

		if(securitySettings().getEnableCSRFProtection())
		{
			logger.info("CSRF protection enabled.");
			http.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
            .csrf().csrfTokenRepository(csrfTokenRepository());
		} else
		{
			logger.warn("CSRF protection disabled!");
			http.csrf().disable();
		}
	}

	private CsrfTokenRepository csrfTokenRepository() {
		/**
		 * Angular sends CSRF token in X-XSRF-TOKEN header instead of X-CSRF-TOKEN
		 */
		final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecuritySettings securitySettings() {
		return new SecuritySettings();
	}
}
