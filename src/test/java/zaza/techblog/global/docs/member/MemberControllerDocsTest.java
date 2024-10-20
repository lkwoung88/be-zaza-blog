package zaza.techblog.global.docs.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import zaza.techblog.global.common.member.controller.MemberController;
import zaza.techblog.global.common.member.entity.Member;
import zaza.techblog.global.common.member.request.JoinMemberRequest;
import zaza.techblog.global.common.member.service.MemberService;
import zaza.techblog.global.common.request.PageSearchRequest;
import zaza.techblog.global.common.result.PageSelectResult;
import zaza.techblog.global.docs.RestDocsSupport;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        JoinMemberRequest request = JoinMemberRequest.builder()
                .id("minsuck")
                .name("minsuck")
                .email("minsuck@test.com")
                .password("*********")
                .build();

        willDoNothing().given(memberService).joinZazaMember(any(Member.class));

        mockMvc.perform(
                        post("/members/join")
                                .content(objectMapper.writeValueAsString(request))
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
                                fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                        .description("timestamp"),
                                fieldWithPath("responseCode.code").type(JsonFieldType.NUMBER)
                                        .description("responseCode.code"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING)
                                        .description("responseCode.message"),
                                fieldWithPath("detailMessage").type(JsonFieldType.STRING)
                                        .description("detailMessage")
                        )
                ));
    }

    @DisplayName("멤버조회 API")
    @Test
    void searchMembers() throws Exception {
        PageSearchRequest request = PageSearchRequest.builder()
                .page(0)
                .limit(10)
                .build();

        given(memberService.searchMembers(any(PageSearchRequest.class)))
                .willReturn(new PageSelectResult<>(List.of(), 0));

        mockMvc.perform(
                        get("/members?page=1&limit=10")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member-search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("page").description("요청할 페이지 번호"),
                                parameterWithName("limit").description("한 페이지에 포함될 회원 수")
                        ),
                        responseFields(
                                fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                        .description("timestamp"),
                                fieldWithPath("responseCode.code").type(JsonFieldType.NUMBER)
                                        .description("responseCode.code"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING)
                                        .description("responseCode.message"),
                                fieldWithPath("detailMessage").type(JsonFieldType.STRING)
                                        .description("detailMessage"),
                                fieldWithPath("data.data").type(JsonFieldType.ARRAY)
                                        .description("detailMessage"),
                                fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER)
                                        .description("totalPage")
                        )
                ));
    }


}
