package pl.edu.pw.elka.rso.eres3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .and().authorizeRequests().antMatchers("/persons").hasRole("ADMIN")
                .and().formLogin();
    }

    @Autowired
    private void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().withUser("aaa").password("sss").roles("USER", "ADMIN")
                .and().withUser("bbb").password("nnn").roles("USER");
    }

    //    @Configuration
//    protected static class AuthenticationConfiguration extends
//            GlobalAuthenticationConfigurerAdapter {
//
//        @Autowired
//        JpaBasedUserDetailsService userDetailsService;
//
//        @Override
//        public void init(AuthenticationManagerBuilder auth) throws Exception {
//            auth
//                    .userDetailsService(userDetailsService)
//                    .passwordEncoder(new BCryptPasswordEncoder());
//        }
//    }
}