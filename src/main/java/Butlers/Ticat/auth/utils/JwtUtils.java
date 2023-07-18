package Butlers.Ticat.auth.utils;

import Butlers.Ticat.auth.jwt.JwtTokenizer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtException;

import javax.servlet.http.HttpServletRequest;
import java.security.SignatureException;
import java.util.Map;

@RequiredArgsConstructor
public class JwtUtils {
    private final JwtTokenizer jwtTokenizer;

    public Map<String, Object> getJwsClaimsFromRequest(HttpServletRequest request) throws SignatureException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new SignatureException("유효하지 않은 토큰입니다.");
            }

            String jws = authorizationHeader.substring(7);
            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
            Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

            return claims;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "만료된 토큰입니다.");
        } catch (SignatureException e) {
            throw new SignatureException("유효하지 않은 토큰입니다.");
        } catch (Exception e) {
            throw new SignatureException("토큰 처리 중 오류가 발생했습니다.");
        }
    }

    public String getUserIdFromToken(String token) throws JwtException {
        try {
            Jws<Claims> claims = jwtTokenizer.getClaims(token, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));
            return claims.getBody().getSubject();
        } catch (JwtException e) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        }
    }
}
