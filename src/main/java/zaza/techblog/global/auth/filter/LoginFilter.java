package zaza.techblog.global.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zaza.techblog.global.auth.user.BasicUserDetails;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.utils.JasonWebTokenUtils;

import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JasonWebTokenUtils jasonWebTokenUtils;

    public LoginFilter(AuthenticationManager authenticationManager, JasonWebTokenUtils jasonWebTokenUtils) {
        this.authenticationManager = authenticationManager;
        this.jasonWebTokenUtils = jasonWebTokenUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // TODO 성공로그

        BasicUserDetails userDetails = (BasicUserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        RoleCode role = userDetails.getRole();

        String token = jasonWebTokenUtils.createJwt(username, role);

        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // TODO 실패로그

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
