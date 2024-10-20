package zaza.techblog.global.common.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.global.common.member.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static zaza.techblog.global.common.code.RoleCode.ROLE_USER;
import static zaza.techblog.global.common.code.StatusCode.ACTIVE;
import static zaza.techblog.global.common.code.StatusCode.INACTIVE;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버 엔티티를 저장한다.")
    @Test
    void saveMember(){

        // given
        Member saveRequestMember = this.createMember("AAAAA", "AAAAA@test.com", "AAA", ROLE_USER, ACTIVE);

        // when
        memberRepository.save(saveRequestMember);
        Optional<Member> savedMember = memberRepository.findByIdAndStatus(saveRequestMember.getId(), saveRequestMember.getStatus());

        // then
        assertThat(savedMember).isNotNull();
        assertThat(saveRequestMember).isEqualTo(savedMember.get());
    }

    @DisplayName("사용자의 id와 활동상태를 조건으로 조회한다.")
    @Test
    void findMemberByIdAndStatus(){

        // given
        Member saveRequestMember_1 = this.createMember("AAAAA", "AAAAA@test.com", "AAA", ROLE_USER, ACTIVE);
        Member saveRequestMember_2 = this.createMember("BBBBB", "BBBBB@test.com", "BBB", ROLE_USER, INACTIVE);
        Member saveRequestMember_3 = this.createMember("CCCCC", "CCCCC@test.com", "CCC", ROLE_USER, ACTIVE);

        memberRepository.saveAll(Arrays.asList(saveRequestMember_1, saveRequestMember_2, saveRequestMember_3));

        // when
        Optional<Member> someMember = memberRepository.findByIdAndStatus(saveRequestMember_3.getId(), saveRequestMember_3.getStatus());

        // then
        assertThat(someMember).isNotNull();
        assertThat(someMember.get().getId()).isEqualTo(saveRequestMember_3.getId());
    }

    @DisplayName("활동상태의 모든 멤버를 조회한다,")
    @Test
    void selectAllMembers(){

        // given
        Member saveRequestMember_1 = this.createMember("AAAAA", "AAAAA@test.com", "AAA", ROLE_USER, ACTIVE);
        Member saveRequestMember_2 = this.createMember("BBBBB", "BBBBB@test.com", "BBB", ROLE_USER, INACTIVE);
        Member saveRequestMember_3 = this.createMember("CCCCC", "CCCCC@test.com", "CCC", ROLE_USER, ACTIVE);

        // when
        memberRepository.saveAll(Arrays.asList(saveRequestMember_1, saveRequestMember_2, saveRequestMember_3));
        List<Member> members = memberRepository.findByStatusOrderByName(ACTIVE, PageRequest.of(0, 10));

        // then
        assertThat(members).hasSize(2)
                .extracting("id", "name", "status")
                .containsExactly(
                        tuple("AAAAA", "AAA", ACTIVE),
                        tuple("CCCCC", "CCC", ACTIVE)
                );
    }

    @DisplayName("활동상태의 모든 멤버 수를 조회한다.")
    @Test
    void countAllMembers(){

        // given
        Member saveRequestMember_1 = this.createMember("AAAAA", "AAAAA@test.com", "AAA", ROLE_USER, ACTIVE);
        Member saveRequestMember_2 = this.createMember("BBBBB", "BBBBB@test.com", "BBB", ROLE_USER, INACTIVE);
        Member saveRequestMember_3 = this.createMember("CCCCC", "CCCCC@test.com", "CCC", ROLE_USER, ACTIVE);

        // when
        memberRepository.saveAll(Arrays.asList(saveRequestMember_1, saveRequestMember_2, saveRequestMember_3));
        int count = memberRepository.countByStatus(ACTIVE);

        // then
        assertThat(count).isEqualTo(2);
    }

    /* ******************** test utils ******************** */

    private Member createMember(String id, String email, String name, RoleCode roleCode, StatusCode statusCode) {
        return Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .oAuthProvider("")
                .password("")
                .role(roleCode)
                .status(statusCode)
                .build();
    }
}