package zaza.techblog.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.DuplicateMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zaza.techblog.domain.member.entity.Member;
import zaza.techblog.global.common.request.PageSearchRequest;
import zaza.techblog.global.common.response.result.PageSelectResult;

import java.util.List;

import static org.hibernate.DuplicateMappingException.Type.ENTITY;
import static zaza.techblog.global.common.code.StatusCode.ACTIVE;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void joinZazaMember(Member member) {

        if(this.checkDuplicate(member.getId())){

            throw new DuplicateMappingException(ENTITY, member.getId());
        }

        memberRepository.save(member);
    }

    public PageSelectResult<List<Member>> searchMembers(PageSearchRequest request) {

        List<Member> members = this.selectMembers(PageRequest.of(request.getPage(), request.getLimit()));
        int totalCount = this.countMembers();

        return new PageSelectResult<>(members, totalCount, request.getLimit());
    }

    /* ******************** private method ******************** */

    private boolean checkDuplicate(String id) {

        return memberRepository.findByIdAndStatus(id, ACTIVE).isPresent();
    }

    private List<Member> selectMembers(Pageable pageable) {

        return memberRepository.findByStatusOrderByName(ACTIVE, pageable);
    }

    private int countMembers() {

        return memberRepository.countByStatus(ACTIVE);
    }
}
