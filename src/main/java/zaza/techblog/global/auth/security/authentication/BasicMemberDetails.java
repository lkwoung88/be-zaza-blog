package zaza.techblog.global.auth.security.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zaza.techblog.domain.member.entity.Member;

import java.util.Collection;

public class BasicMemberDetails implements UserDetails {

    private Member member;

    public BasicMemberDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
}
