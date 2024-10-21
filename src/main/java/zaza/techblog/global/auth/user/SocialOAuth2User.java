package zaza.techblog.global.auth.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.domain.member.dto.MemberDto;
import zaza.techblog.domain.member.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SocialOAuth2User implements OAuth2User {

    private final MemberDto memberDto;

    public SocialOAuth2User(Member member) {

        this.memberDto = MemberDto.of(member);
    }

    public SocialOAuth2User(MemberDto memberDto) {

        this.memberDto = memberDto;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of();
    }

    @Override
    public String getName() {

        return memberDto.getName();
    }

    public String getUsername() {

        return memberDto.getId();
    }

    public RoleCode getRole(){

        return memberDto.getRole();
    }
}
