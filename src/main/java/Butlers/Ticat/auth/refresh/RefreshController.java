package Butlers.Ticat.auth.refresh;

import Butlers.Ticat.auth.jwt.JwtTokenizer;
import Butlers.Ticat.auth.jwt.TokenService;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/refresh")
@RequiredArgsConstructor
public class RefreshController {
    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;


    // 리프레시 토큰 재발급
    @PostMapping
    public ResponseEntity<String> refreshAccessToken(HttpServletRequest request) {
        String refreshTokenHeader = request.getHeader("Refresh");
        if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
            String refreshToken = refreshTokenHeader.substring(7);
            try {
                Optional<RefreshToken> refreshTokenObj = refreshTokenRepository.findById(refreshToken);
                if (!refreshTokenObj.isPresent()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 리프레시 토큰");
                }

                Jws<Claims> claims = jwtTokenizer.getClaims(refreshToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));

                String id = claims.getBody().getSubject();
                Optional<Member> optionalMember = memberRepository.findById(id);

                if (optionalMember.isPresent()) {
                    Member member = optionalMember.get();
                    String accessToken = tokenService.delegateAccessToken(member);

                    // 액세스 토큰의 만료 시간을 가져옵니다.
                    Date accessTokenExpiration = tokenService.getAccessTokenExpiration();

                    // 날짜 포맷을 "yyyy-MM-dd HH:mm:ss"으로 변경
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String accessTokenExpirationFormatted = dateFormat.format(accessTokenExpiration);

                    // 응답 헤더에 액세스 토큰과 만료 시간 설정
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Authorization", accessToken);
                    headers.add("AccessTokenExpiration", accessTokenExpirationFormatted);

                    return ResponseEntity.ok().headers(headers).body("액세스 토큰이 갱신되었습니다");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 회원 ID");
                }
            } catch (JwtException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 리프레시 토큰");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리프레시 토큰이 누락되었습니다");
        }
    }

    // 로그아웃 시 리프레시 토큰 삭제
    @DeleteMapping
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String refreshTokenHeader = request.getHeader("Refresh");
        if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
            String refreshToken = refreshTokenHeader.substring(7);
            try {
                Optional<RefreshToken> refreshTokenObj = refreshTokenRepository.findById(refreshToken);
                if (!refreshTokenObj.isPresent()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 리프레시 토큰 [Redis]");
                }

                refreshTokenRepository.deleteById(refreshToken);
                return ResponseEntity.ok().body("로그아웃이 완료되었습니다, 리프레시 토큰이 삭제되었습니다");

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리프레시 토큰 삭제 중 오류가 발생했습니다");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리프레시 토큰이 누락되었습니다");
        }
    }
}
