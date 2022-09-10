package com.alexbonetskiy.orderservice.config;


import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import static java.util.Objects.isNull;

@Slf4j
@Configuration
public class Oauth2FeignClientConfig {


    @Value("${spring.security.oauth2.client.registration.order-service.client-id}")
    private String clientName;

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public Oauth2FeignClientConfig(OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
                            ClientRegistrationRepository clientRegistrationRepository) {
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }


    @Bean
    public RequestInterceptor requestInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
        var clientRegistration = clientRegistrationRepository.findByRegistrationId(clientName);
        var clientCredentialsFeignManager = new OAuthClientCredentialsFeignManager();
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + clientCredentialsFeignManager.getAccessToken(authorizedClientManager, clientRegistration));
    }


    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager() {
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }

    static class OAuthClientCredentialsFeignManager {
        public String getAccessToken(OAuth2AuthorizedClientManager manager, ClientRegistration clientRegistration) {
            try {
                var oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                        .withClientRegistrationId(clientRegistration.getRegistrationId())
                        .principal(SecurityContextHolder.getContext().getAuthentication())
                        .build();
                var client = manager.authorize(oAuth2AuthorizeRequest);
                if (isNull(client)) {
                    throw new IllegalStateException("client credentials flow on " + clientRegistration.getRegistrationId() + " failed, client is null");
                }
                return client.getAccessToken().getTokenValue();
            } catch (Exception ex) {
                log.error("client credentials error " + ex.getMessage());
            }
            return null;
        }
    }
}
