package Butlers.Ticat.auth.handler;

import Butlers.Ticat.auth.jwt.JwtTokenizer;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        Member member;
        try {
            member = saveMember(email);
            redirect(request, response, member);
        } catch (Exception e) {
            redirect(request, response, memberService.findMemberByEmail(email));
        }
    }

    private Member saveMember(String email) {
        Member member = new Member(email);
        return memberService.joinInOauth(member);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException {
        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member.getEmail());
        String addedAccessToken = "Bearer " + accessToken;

        response.setHeader("Authorization", addedAccessToken);
        response.setHeader("Refresh", refreshToken);

        String uri = createUri(addedAccessToken, member.getMemberId()).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("email", member.getEmail());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String username) {
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    private URI createUri(String accessToken, long memberId) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("Authorization", accessToken);
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .port(3000)
                .queryParams(queryParams)
                .build()
                .toUri();

    }

}
