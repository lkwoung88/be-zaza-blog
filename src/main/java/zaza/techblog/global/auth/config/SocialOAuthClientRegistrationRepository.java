package zaza.techblog.global.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class SocialOAuthClientRegistrationRepository {

    private final SocialOAuthClientRegistration socialOAuthClientRegistration;

    @Autowired
    public SocialOAuthClientRegistrationRepository(SocialOAuthClientRegistration socialOAuthClientRegistration) {
        this.socialOAuthClientRegistration = socialOAuthClientRegistration;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                socialOAuthClientRegistration.naverClientRegistration(),
                socialOAuthClientRegistration.googleClientRegistration()
        );
    }
}
