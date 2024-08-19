package zaza.techblog.global.auth.security.authentication;

import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Setter
@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {

    private final BasicUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public BasicAuthenticationProvider(BasicUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String requestPassword = authentication.getPrincipal().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        if(!passwordEncoder.matches(requestPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
