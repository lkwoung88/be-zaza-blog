package zaza.techblog.global.common.member.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zaza.techblog.global.common.member.entity.Member;
import zaza.techblog.global.common.member.repository.MemberRepository;
import zaza.techblog.global.common.response.BaseResponse;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public BaseResponse joinZazaMember(Member member) {
        memberRepository.save(member);
        return BaseResponse.ofSuccess();
    }
}
