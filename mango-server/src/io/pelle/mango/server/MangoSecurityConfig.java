package io.pelle.mango.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.server.security.MangoAccessVoter;
import io.pelle.mango.server.security.MangoAffirmativeBased;

@EnableWebSecurity
@Configuration
@Order(10)
public class MangoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

	@Autowired
	private IPropertyService propertyService;

	@Bean
	public AffirmativeBased accessDecisionManager() {

		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();

		decisionVoters.add(mangoAccessVoter());
		decisionVoters.add(webExpressionVoter());
		decisionVoters.add(authenticatedVoter());

		return new MangoAffirmativeBased(decisionVoters);
	}

	@Bean
	public MangoAccessVoter mangoAccessVoter() {
		return new MangoAccessVoter(propertyService);
	}

	@Bean
	public WebExpressionVoter webExpressionVoter() {
		return new WebExpressionVoter();
	}

	@Bean
	public AuthenticatedVoter authenticatedVoter() {
		return new AuthenticatedVoter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().accessDecisionManager(accessDecisionManager()).anyRequest().authenticated();
	}

}