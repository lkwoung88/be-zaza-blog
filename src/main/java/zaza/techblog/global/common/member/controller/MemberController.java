package zaza.techblog.global.common.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zaza.techblog.global.common.member.request.JoinMemberRequest;
import zaza.techblog.global.common.member.service.MemberService;
import zaza.techblog.global.common.response.BaseResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/join")
    public ResponseEntity<BaseResponse> joinZazaMember(@RequestBody @Valid JoinMemberRequest request, BindingResult result) {
        BaseResponse response = memberService.joinZazaMember(request.toEntity());
        return ResponseEntity.ok(response);
    }

}
