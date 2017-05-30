package pl.edu.pw.elka.rso.eres3.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix="app.security", ignoreUnknownFields=false)
public class SecuritySettings {
	@NotNull
	private boolean requireAuthentication;
	@NotNull
	private boolean enableCSRFProtection;
	@NotNull
	private boolean checkPermissions;

    public boolean getRequireAuthentication() {
    	return requireAuthentication;
    }

    public boolean getEnableCSRFProtection() {
    	return enableCSRFProtection;
    }

    public void setRequireAuthentication(final boolean value) {
    	requireAuthentication = value;
    }

    public void setEnableCSRFProtection(final boolean value) {
    	enableCSRFProtection = value;
    }

	public boolean isCheckPermissions() {
		return checkPermissions;
	}

	public void setCheckPermissions(final boolean checkPermissions) {
		this.checkPermissions = checkPermissions;
	}
}