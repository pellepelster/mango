package io.pelle.mango.server.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.server.ConfigurationParameters;

public class MangoAccessVoter implements AccessDecisionVoter<Object> {

	private boolean authorizationDisabled = false;

	public MangoAccessVoter(IPropertyService propertyService) {
		super();
		authorizationDisabled = propertyService.getProperty(ConfigurationParameters.SECURITY_DISABLE);
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

		if (authorizationDisabled) {
			return AccessDecisionVoter.ACCESS_GRANTED;
		} else {
			return AccessDecisionVoter.ACCESS_DENIED;
		}

	}

}
