package Butlers.Ticat.auth.handler;

import Butlers.Ticat.auth.jwt.JwtTokenizer;
import Butlers.Ticat.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;

    public MemberAuthenticationSuccessHandler(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("# Authentication successfully!");

        Object principal = authentication.getPrincipal();
        if (principal instanceof Member) {
            Member member = (Member) principal;

            if (member.getDisplayName() == null || member.getInterest().getCategories() == null) {
                log.info("# Need Resist Interest");
                String accessToken = generateAccessToken(member);
                String refreshToken = generateRefreshToken(member.getId());
                String addedAccessToken = "Bearer " + accessToken;

                response.setHeader("Authorization", addedAccessToken);
                response.setHeader("Refresh", refreshToken);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("닉네임 설정 및 관심사 등록이 필요합니다."); // 클라이언트에게 관심사 등록이 필요하다는 정보를 전달
                response.getWriter().flush();
                return;
            }
        }

        // 관심사 등록이 필요하지 않은 경우
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String generateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("id", member.getId());

        String subject = member.getId();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String generateRefreshToken(String id) {
        String subject = id;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
