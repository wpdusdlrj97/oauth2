package org.client.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {
	
	@Autowired private ClientTokenServices clientTokenService;
	@Autowired private OAuth2ClientContext oauth2ClientContext;
	
	/*
	 * 클라이언트 관련 정보 및 인증서버 엔드포인트 설정
	 */
	@Bean
	public OAuth2ProtectedResourceDetails authorizationCode() {
		AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
		
		resourceDetails.setId("resource");
		resourceDetails.setTokenName("oauth_token");
		resourceDetails.setClientId("689a135e-7d6a-448f-be67-760dda1d7383");
		resourceDetails.setClientSecret("6163e2c9-dca7-49cf-9203-8f24263f3534");
		resourceDetails.setAccessTokenUri("http://localhost:8080/oauth/token");
		resourceDetails.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
		resourceDetails.setScope(Arrays.asList("read"));
		resourceDetails.setPreEstablishedRedirectUri("http://localhost:9000/callback");
		resourceDetails.setUseCurrentUri(false);
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
		
		return resourceDetails;
	}
	
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate() {
		OAuth2ProtectedResourceDetails resourceDetails = authorizationCode();
		
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
		
		AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
		provider.setClientTokenServices(clientTokenService);
		restTemplate.setAccessTokenProvider(provider);
		
		return restTemplate;
	}
}
