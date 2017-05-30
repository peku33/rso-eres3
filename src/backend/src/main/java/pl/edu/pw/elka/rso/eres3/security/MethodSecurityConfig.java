package pl.edu.pw.elka.rso.eres3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import pl.edu.pw.elka.rso.eres3.security.domain.DomainPermissionEvaluator;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

	private final DomainPermissionEvaluator permissionEvaluator;

	@Autowired
	public MethodSecurityConfig(final DomainPermissionEvaluator permissionEvaluator) {
		this.permissionEvaluator = permissionEvaluator;
	}

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {

		final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(permissionEvaluator);
		return expressionHandler;
	}
}