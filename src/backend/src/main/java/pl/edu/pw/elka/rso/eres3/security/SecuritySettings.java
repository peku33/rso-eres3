package pl.edu.pw.elka.rso.eres3.security;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="app.security", ignoreUnknownFields=false)
public class SecuritySettings {
	@NotNull
	private boolean requireAuthentication;
	@NotNull
	private boolean enableCSRFProtection;

    public boolean getRequireAuthentication() {
    	return requireAuthentication;
    }
    
    public boolean getEnableCSRFProtection() {
    	return enableCSRFProtection;
    }
    
    public void setRequireAuthentication(boolean value) {
    	requireAuthentication = value;
    }
    
    public void setEnableCSRFProtection(boolean value) {
    	enableCSRFProtection = value;
    }
}