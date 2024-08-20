package zaza.techblog.global.auth.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import zaza.techblog.domain.member.entity.Member;
import zaza.techblog.domain.member.repository.MemberRepository;

@Slf4j
@Component
public class BasicUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public BasicUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

         Member member = memberRepository.findByIdAndStatus(username, "active").orElseThrow(
                 () -> new UsernameNotFoundException("ID Not Found : " + username));

        return new BasicUserDetails(member);
    }
}
