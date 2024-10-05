package zaza.techblog.global.common.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.global.common.member.entity.Member;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버 회원가입 테스트")
    @Test
    void saveMember(){

    // given
        Member saveRequestMember = createMember();

    // when
        memberRepository.save(saveRequestMember);
        Optional<Member> savedMember = memberRepository.findByIdAndStatus(saveRequestMember.getId(), saveRequestMember.getStatus());

    // then
        assertNotNull(savedMember);
        assertEquals(saveRequestMember, savedMember.get());
    }

    /* ******************** test utils ******************** */

    private static Member createMember() {
        return Member.builder()
                .id("minsuck")
                .email("minsuck@test.com")
                .name("minsuck")
                .oAuthProvider("")
                .password("")
                .role(RoleCode.ROLE_USER)
                .status(StatusCode.ACTIVE)
                .build();
    }
}