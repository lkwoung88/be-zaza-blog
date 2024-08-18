package zaza.techblog.global.auth.security.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import zaza.techblog.domain.member.entity.Member;
import zaza.techblog.domain.member.repository.MemberRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasicMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override

    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

         Member member = memberRepository.findByIdAndStatus(id, "active").orElseThrow(
                 () -> new UsernameNotFoundException("ID Not Found : " + id));


        return new BasicMemberDetails(member);
    }
}
