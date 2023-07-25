package Butlers.Ticat.auth.handler;

import Butlers.Ticat.auth.jwt.TokenService;
import Butlers.Ticat.auth.userinfo.GoogleUserInfo;
import Butlers.Ticat.auth.userinfo.KakaoUserInfo;
import Butlers.Ticat.auth.userinfo.NaverUserInfo;
import Butlers.Ticat.auth.userinfo.OAuth2UserInfo;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        OAuth2UserInfo oAuth2UserInfo = null;
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;

        String provider = oauth2Token.getAuthorizedClientRegistrationId();
        String profileImage = null;

        if(provider.equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
            profileImage = oAuth2UserInfo.getProfileImage();
        } else if(provider.equals("kakao")) {
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
            profileImage = oAuth2UserInfo.getProfileImage();
        } else if(provider.equals("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));

            // 기본 프로필 이미지가 아닐경우에 이미지를 가져옴
            if (!oAuth2UserInfo.getProfileImage().equals("https://ssl.pstatic.net/static/pwe/address/img_profile.png")) {
                profileImage = oAuth2UserInfo.getProfileImage();
            }
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String id = provider + "_" + providerId;
        String email = null;
        Member member = null;

        try {
            member = saveMember(id, email, profileImage);
        } catch (Exception e) {
            member = memberService.findMemberById(id);
        } finally {
            redirect(request, response, member);
        }
    }

    private Member saveMember(String id, String email, String profileImage) {
        Member member = new Member(id, email, profileImage);
        return memberService.joinInOauth(member);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException {
        String accessToken = tokenService.delegateAccessToken(member);
        String refreshToken = tokenService.delegateRefreshToken(member);

        response.setHeader("Authorization", accessToken);
        response.setHeader("Refresh", refreshToken);

        String uri;

        if (member.getDisplayName() == null || member.getInterest().getCategories().size() == 0) {
            uri = createInterestUri(accessToken, refreshToken, member.getMemberId(), member.getDisplayName(), member.getProfileUrl()).toString();
        } else {
            uri = createUri(accessToken, refreshToken, member.getMemberId(), member.getDisplayName(), member.getProfileUrl()).toString();
        }
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    // 콜백 Uri
    private URI createUri(String accessToken, String refreshToken, long memberId, String displayName, String profileUrl) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://d99pqcn6hzkdg.cloudfront.net/callback/true")
                .queryParam("Authorization", accessToken)
                .queryParam("Refresh", refreshToken)
                .queryParam("memberId", memberId)
                .queryParam("displayName", displayName)
                .queryParam("profileUrl", profileUrl);
        return builder.build().toUri();
    }

    // 콜백 Uri(관심사 등록 필요)
    private URI createInterestUri(String accessToken, String refreshToken, long memberId, String displayName, String profileUrl) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://d99pqcn6hzkdg.cloudfront.net/callback/false")
                .queryParam("Authorization", accessToken)
                .queryParam("Refresh", refreshToken)
                .queryParam("memberId", memberId)
                .queryParam("displayName", displayName)
                .queryParam("profileUrl", profileUrl);
        return builder.build().toUri();
    }
}
