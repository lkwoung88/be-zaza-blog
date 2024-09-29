package zaza.techblog.global.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import zaza.techblog.global.auth.filter.JasonWebTokenFilter;
import zaza.techblog.global.auth.filter.LoginFilter;
import zaza.techblog.global.auth.service.SocialOAuth2UserService;
import zaza.techblog.global.auth.handler.SocialOAuthSuccessHandler;
import zaza.techblog.global.utils.JasonWebTokenUtils;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JasonWebTokenUtils jasonWebTokenUtils;
    private final SocialOAuth2UserService socialOAuth2UserService;
    private final SocialOAuthSuccessHandler socialOAuthSuccessHandler;

    public SecurityConfigurer(AuthenticationConfiguration authenticationConfiguration, JasonWebTokenUtils jasonWebTokenUtils, SocialOAuth2UserService socialOAuth2UserService, SocialOAuthSuccessHandler socialOAuthSuccessHandler) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jasonWebTokenUtils = jasonWebTokenUtils;
        this.socialOAuth2UserService = socialOAuth2UserService;
        this.socialOAuthSuccessHandler = socialOAuthSuccessHandler;
    }

    @Bean
    public AuthenticationManager authenticationManger(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        LoginFilter loginFilter = new LoginFilter(authenticationManger(authenticationConfiguration), jasonWebTokenUtils);

        http.csrf((auth) -> auth.disable());
        http.formLogin((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());

        http.oauth2Login((auth) -> auth
                .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig.userService(socialOAuth2UserService))
                .successHandler(socialOAuthSuccessHandler));

        http.authorizeRequests((auth) -> auth
                .requestMatchers("/**", "/login").permitAll()
                .anyRequest().authenticated());

        http.cors(auth -> auth.configurationSource(
                request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Collections.singletonList("http://localhost:9000"));
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setMaxAge(3600L);
                    configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));
                    return configuration;
                }));

        http.sessionManagement((auth) -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JasonWebTokenFilter(jasonWebTokenUtils), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
