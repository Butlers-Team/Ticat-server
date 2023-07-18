package Butlers.Ticat.auth.handler;

import Butlers.Ticat.auth.jwt.TokenService;
import Butlers.Ticat.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("# Authentication successfully!");

        Object principal = authentication.getPrincipal();
        if (principal instanceof Member) {
            Member member = (Member) principal;

            if (member.getDisplayName() == null || member.getInterest().getCategories() == null) {
                log.info("# Need Resist Interest");
                String accessToken = tokenService.delegateAccessToken(member);
                String refreshToken = tokenService.delegateRefreshToken(member);

                response.setHeader("Authorization", accessToken);
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
}
