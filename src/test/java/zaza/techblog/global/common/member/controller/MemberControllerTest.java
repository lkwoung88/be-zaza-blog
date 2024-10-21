package zaza.techblog.global.common.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import zaza.techblog.domain.member.MemberController;
import zaza.techblog.domain.member.request.JoinMemberRequest;
import zaza.techblog.domain.member.MemberService;
import zaza.techblog.global.common.request.PageSearchRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @DisplayName("회원가입을 받는다.")
//    @Test
    void joinZazaMember() throws Exception {

        // given
        JoinMemberRequest request = JoinMemberRequest.builder()
                .id("AAAAA")
                .password("********")
                .email("AAAAA@test.com")
                .name("AAA")
                .build();

        // when & then
        mockMvc.perform(
                post("/members/join")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("SUCCESS"));
    }

    @DisplayName("회원을 조회한다.")
//    @Test
    void searchMembers() throws Exception {

        // given
        PageSearchRequest request = PageSearchRequest.builder()
                .page(0)
                .limit(10)
                .build();

        // when & then
        mockMvc.perform(get("/members") // 실제 엔드포인트 URL로 변경
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPage").value(5));
    }

}