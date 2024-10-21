package zaza.techblog.global.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zaza.techblog.global.auth.response.GoogleOAuthResponse;
import zaza.techblog.global.auth.response.NaverOAuthResponse;
import zaza.techblog.global.auth.response.SocialOAuthResponse;
import zaza.techblog.global.auth.user.SocialOAuth2User;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.domain.member.entity.Member;
import zaza.techblog.domain.member.MemberRepository;

import java.util.Optional;

@Service
public class SocialOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Autowired
    public SocialOAuth2UserService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialOAuthResponse socialOAuthResponse = null;

        if ("naver".equals(registrationId)) {

            socialOAuthResponse = new NaverOAuthResponse(oAuth2User.getAttributes());
        }
        else if ("google".equals(registrationId)) {

            socialOAuthResponse = new GoogleOAuthResponse(oAuth2User.getAttributes());
        }
        else {

            // TODO 여기에서 social oauth type 없음 에러(custom error type?)
            throw new OAuth2AuthenticationException("Social OAuth Type not supported");
        }

        String id = socialOAuthResponse.getProvider() + "-" + socialOAuthResponse.getProviderId();
        Optional<Member> optionalMember = memberRepository.findByIdAndStatus(id, StatusCode.ACTIVE);

        if (optionalMember.isPresent()) {

            Member member = optionalMember.get();
            member.updateMemberByOAuthResponse(member);

            return new SocialOAuth2User(member);
        }
        else {

            Member member = new Member(socialOAuthResponse);
            memberRepository.save(member);

            return new SocialOAuth2User(member);
        }
    }
}
