package pl.edu.pw.elka.rso.eres3.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
      @Override
      public void configure(WebSecurity web) throws Exception {
    	  web.debug(true);
      }
	
	  @Override
      protected void configure(HttpSecurity http) throws Exception {
    	  /**
    	   * Config for authentication and CSRF tokens handling. 
    	   */
    	  http
          	.authorizeRequests()
          	.anyRequest().authenticated()
          	.and()
          		.formLogin()
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
            	.authenticationEntryPoint(new RestAuthenticationEntryPoint())
            .and()
            .csrf().disable();
            /*.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
            .csrf().csrfTokenRepository(csrfTokenRepository());*/
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
          auth
              .inMemoryAuthentication()
                  .withUser("user").password("password").roles("USER");
      }
}
