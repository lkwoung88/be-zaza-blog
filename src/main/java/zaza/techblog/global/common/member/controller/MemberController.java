package zaza.techblog.global.common.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zaza.techblog.global.common.member.request.JoinMemberRequest;
import zaza.techblog.global.common.member.service.MemberService;
import zaza.techblog.global.common.response.BaseResponse;
import zaza.techblog.global.common.member.entity.Member;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/join")
    public ResponseEntity<BaseResponse> joinZazaMember(@RequestBody @Valid JoinMemberRequest request, BindingResult result) {
        BaseResponse response = memberService.joinZazaMember(new Member(request));
        return ResponseEntity.ok(response);
    }

}
