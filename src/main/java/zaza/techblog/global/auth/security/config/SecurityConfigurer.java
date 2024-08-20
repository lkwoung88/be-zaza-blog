package zaza.techblog.global.auth.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zaza.techblog.global.auth.jwt.JasonWebTokenUtils;
import zaza.techblog.global.auth.security.authentication.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JasonWebTokenUtils jasonWebTokenUtils;

    public SecurityConfigurer(AuthenticationConfiguration authenticationConfiguration,
                              JasonWebTokenUtils jasonWebTokenUtils) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jasonWebTokenUtils = jasonWebTokenUtils;
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
        http.httpBasic((auth)-> auth.disable());
        http.authorizeRequests((auth) -> auth
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated());

//        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
