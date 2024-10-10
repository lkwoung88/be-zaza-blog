package zaza.techblog.global.docs.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import zaza.techblog.global.common.member.controller.MemberController;
import zaza.techblog.global.common.member.entity.Member;
import zaza.techblog.global.common.member.request.JoinMemberRequest;
import zaza.techblog.global.common.member.service.MemberService;
import zaza.techblog.global.common.response.BaseResponse;
import zaza.techblog.global.docs.RestDocsSupport;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerDocsTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);

    @Override
    protected Object initController() {

        return new MemberController(memberService);
    }

    @DisplayName("신규회원가입 API")
    @Test
    void joinMemberTest() throws Exception {

        JoinMemberRequest joinMemberRequest = JoinMemberRequest.builder()
                .id("minsuck")
                .name("minsuck")
                .email("minsuck@test.com")
                .password("*********")
                .build();

        given(memberService.joinZazaMember(any(Member.class)))
                .willReturn(BaseResponse.ofSuccess());

        mockMvc.perform(
                        post("/members/join")
                                .content(objectMapper.writeValueAsString(joinMemberRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member-join",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING)
                                        .description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("name"),
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("email"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("password")
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.NUMBER)
                                        .description("responseCode.code"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING)
                                        .description("responseCode.message"),
                                fieldWithPath("detailMessage").type(JsonFieldType.STRING)
                                        .description("detailMessage")
                        )
                ));

    }
}
