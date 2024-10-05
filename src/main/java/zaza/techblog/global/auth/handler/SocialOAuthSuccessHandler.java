package zaza.techblog.global.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.utils.JasonWebTokenUtils;
import zaza.techblog.global.auth.user.SocialOAuth2User;

import java.io.IOException;

@Slf4j
@Component
public class SocialOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JasonWebTokenUtils jasonWebTokenUtils;

    public SocialOAuthSuccessHandler(JasonWebTokenUtils jasonWebTokenUtils) {
        this.jasonWebTokenUtils = jasonWebTokenUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SocialOAuth2User userDetails = (SocialOAuth2User) authentication.getPrincipal();
        String username = userDetails.getUsername();
        RoleCode role = userDetails.getRole();

        String jwt = jasonWebTokenUtils.createJwt(username, role);

        response.addCookie(createCookie("Authorization", jwt));
        response.sendRedirect("http://localhost:9000/");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(60*60*60*60);

        return cookie;
    }
}
