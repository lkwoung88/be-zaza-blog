package zaza.techblog.global.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zaza.techblog.global.auth.user.BasicUserDetails;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.domain.member.dto.MemberDto;
import zaza.techblog.domain.member.entity.Member;
import zaza.techblog.domain.member.MemberRepository;

@Slf4j
@Service
public class BasicUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public BasicUserDetailsService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByIdAndStatus(username, StatusCode.ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("ID Not Found : " + username));

        return new BasicUserDetails(MemberDto.of(member));
    }
}
