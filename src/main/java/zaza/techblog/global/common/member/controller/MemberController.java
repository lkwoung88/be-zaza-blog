package zaza.techblog.global.common.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zaza.techblog.global.common.member.request.JoinMemberRequest;
import zaza.techblog.global.common.member.service.MemberService;
import zaza.techblog.global.common.request.PageSearchRequest;
import zaza.techblog.global.handler.response.type.BaseResponse;
import zaza.techblog.global.handler.response.type.DataResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public BaseResponse joinZazaMember(@RequestBody @Valid JoinMemberRequest request, BindingResult result) {

        memberService.joinZazaMember(request.toEntity());

        return BaseResponse.ofSuccess();
    }

    @GetMapping
    public DataResponse searchMembers(@ModelAttribute @Valid PageSearchRequest request) {

        return DataResponse.ofSuccess(memberService.searchMembers(request));
    }
}
