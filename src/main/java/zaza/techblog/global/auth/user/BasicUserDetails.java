package zaza.techblog.global.auth.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.common.member.dto.MemberDto;

import java.util.ArrayList;
import java.util.Collection;

public class BasicUserDetails implements UserDetails {

    private MemberDto memberDto;

    public BasicUserDetails(MemberDto memberDto) {
        this.memberDto = memberDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberDto.getRole().getCodename();
            }
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return memberDto.getPassword();
    }

    @Override
    public String getUsername() {
        return memberDto.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public RoleCode getRole() {
        return memberDto.getRole();
    }
}
