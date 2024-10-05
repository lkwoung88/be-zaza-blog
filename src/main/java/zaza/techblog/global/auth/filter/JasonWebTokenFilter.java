package zaza.techblog.global.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.utils.JasonWebTokenUtils;
import zaza.techblog.global.auth.user.SocialOAuth2User;
import zaza.techblog.global.common.member.dto.MemberDto;

import java.io.IOException;

public class JasonWebTokenFilter extends OncePerRequestFilter {

    private final JasonWebTokenUtils jasonWebTokenUtils;

    public JasonWebTokenFilter(JasonWebTokenUtils jasonWebTokenUtils) {
        this.jasonWebTokenUtils = jasonWebTokenUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        if(authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // TODO 유효기간 검사
        String username = jasonWebTokenUtils.getUsername(authorization);
        RoleCode role = jasonWebTokenUtils.getRole(authorization);

        MemberDto memberDto = new MemberDto(username, role);
        SocialOAuth2User socialOAuth2User = new SocialOAuth2User(memberDto);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(socialOAuth2User, null, socialOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);
    }
}
