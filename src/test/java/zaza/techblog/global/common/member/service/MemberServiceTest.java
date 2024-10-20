package zaza.techblog.global.common.member.service;

import org.hibernate.DuplicateMappingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.global.common.member.entity.Member;
import zaza.techblog.global.common.member.repository.MemberRepository;
import zaza.techblog.global.common.request.PageSearchRequest;
import zaza.techblog.global.common.result.PageSelectResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static zaza.techblog.global.common.code.RoleCode.ROLE_USER;
import static zaza.techblog.global.common.code.StatusCode.ACTIVE;
import static zaza.techblog.global.common.code.StatusCode.INACTIVE;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @DisplayName("넘어온 멤버의 데이터를 바탕으로 회원가입")
    @Test
    void joinMember(){

        // given
        Member saveRequestMember = this.createMember("AAAAA", "AAAAA@test.com", "AAA", ROLE_USER, ACTIVE);

        // when
        memberService.joinZazaMember(saveRequestMember);
        Optional<Member> savedMember = memberRepository.findByIdAndStatus(saveRequestMember.getId(), ACTIVE);

        // then
        assertThat(savedMember).isNotNull();
        assertThat(saveRequestMember.getId()).isEqualTo(savedMember.get().getId());
    }

    @DisplayName("중복된 아이디에 대한 회원가입 요청에는 DuplicateMappingException 예외를 던진다.")
    @Test
    void joinMember_DuplicationCheck(){

        // given
        Member saveRequestMember_1 = this.createMember("AAAAA", "AAAAA@test.com", "AAA", ROLE_USER, ACTIVE);
        Member saveRequestMember_2 = this.createMember("AAAAA", "AAAAB@test.com", "AAB", ROLE_USER, ACTIVE);

        memberRepository.save(saveRequestMember_1);

        // when & then
        assertThatThrownBy(() -> {memberService.joinZazaMember(saveRequestMember_2);})
                .isInstanceOf(DuplicateMappingException.class)
                .hasMessage("Duplicate entity definition 'AAAAA'");
    }

    @DisplayName("활동 중인 멤버를 이름순으로 조회한다.")
    @Test
    void searchMembers(){

        // given
        Member saveRequestMember_1 = this.createMember("AAAAA", "AAAAA@test.com", "AAA", ROLE_USER, ACTIVE);
        Member saveRequestMember_2 = this.createMember("BBBBB", "BBBBB@test.com", "BBB", ROLE_USER, INACTIVE);
        Member saveRequestMember_3 = this.createMember("CCCCC", "CCCCC@test.com", "CCC", ROLE_USER, ACTIVE);

        PageSearchRequest pageSearchRequest = new PageSearchRequest(1, 10);

        // when
        memberRepository.saveAll(Arrays.asList(saveRequestMember_1, saveRequestMember_2, saveRequestMember_3));
        PageSelectResult<List<Member>> selectResult = memberService.searchMembers(pageSearchRequest);

        // then
        assertThat(selectResult).isNotNull();
        assertThat(selectResult.getTotalPage()).isEqualTo(1);
        assertThat(selectResult.getData()).hasSize(2)
                .extracting("id", "name", "status")
                .containsExactly(
                        tuple("AAAAA", "AAA", ACTIVE),
                        tuple("CCCCC", "CCC", ACTIVE)
                );
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