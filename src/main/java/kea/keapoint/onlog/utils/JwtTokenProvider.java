package kea.keapoint.onlog.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kea.keapoint.onlog.base.Role;
import kea.keapoint.onlog.dto.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private Key secretKey;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(key));
    }

    private static final long ONE_HOUR = 1000L * 60 * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = ONE_HOUR * 3; // 3 hours
    private static final long REFRESH_TOKEN_EXPIRE_TIME = ONE_DAY * 5; // 5 days

    /**
     * Authentication 객체와 사용자 ID를 입력받아, JWT 액세스 토큰과 리프레시 토큰을 생성한다.
     *
     * @param authentication 인증 정보를 담고 있는 객체. 이 객체에는 사용자의 권한 정보 등이 포함되어 있다.
     * @param userIdx        사용자의 고유 ID.
     * @return 생성된 JWT 액세스 토큰과 리프레시 토큰을 담고 있는 Token 객체
     */
    public TokenDto createTokens(Authentication authentication, UUID userIdx) {
        // 사용자의 역할을 가져와서 Role 객체로 변환한다.
        Role roles = authentication.getAuthorities().stream()
                .map(authority -> Role.valueOf(authority.getAuthority().toUpperCase()))
                .toList()
                .get(0);

        // 생성된 액세스 토큰과 리프레시 토큰을 포함하는 Token 객체를 반환한다.
        return new TokenDto(
                roles,
                createAccessToken(authentication, userIdx),
                createRefreshToken(authentication, userIdx)
        );
    }

    /**
     * 액세스 토큰을 생성하는 메소드
     *
     * @param authentication 사용자 인증 정보를 담고 있는 Authentication 객체
     * @param userIdx        사용자의 고유 ID.
     * @return 생성된 JWT 액세스 토큰
     */
    public String createAccessToken(Authentication authentication, UUID userIdx) {
        // 사용자의 역할을 가져와서 콤마로 분리된 문자열로 변환한다.
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 액세스 토큰을 생성하고 반환한다.
        return Jwts.builder()
                // Header: 토큰의 타입(JWT)과 서명에 사용된 알고리즘(HS512) 정보를 담는다.
                .header()
                .add("type", "JWT") // 토큰의 타입 지정. 여기서는 JWT를 사용.
                .and()

                // Payload: 토큰에 담을 클레임(데이터)을 지정. 클레임에는 사용자의 이름, 역할, ID 등이 포함될 수 있다.
                .issuer("OnLog-SERVER") // iss 클레임: 토큰 발급자를 지정
                .subject(authentication.getName()) // sub 클레임: 토큰 제목을 지정
//                .audience().add("your-audience").and()  // aud 클레임: 토큰 대상자를 지정
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME)) // exp 클레임: 토큰 만료 시간을 지정
//                .notBefore(Date(System.currentTimeMillis())) // nbf 클레임: 토큰 활성 날짜를 지정. 헤딩 시간 이전에는 토큰이 활성화되지 않는다.
                .issuedAt(new Date()) // iat 클레임: 토큰 발급 시간을 지정
//                .id(UUID.randomUUID().toString()) // jti 클레임: JWT 토큰 식별자를 지정
                .claim("role", roles) // 사용자 정의 클레임: 사용자의 역할
                .claim("client-id", userIdx) // 사용자 정의 클레임: 사용자의 식별자

                // Signature: header와 payload를 암호화한 결과. 이 부분이 토큰의 무결성을 보장하는 부분
                .signWith(secretKey) // signWith 메소드를 사용해 서명 알고리즘과 키를 지정

                // compact 메소드를 호출해 JWT 토큰 문자열을 생성한다.
                .compact();
    }

    /**
     * 리프레시 토큰을 생성하는 메소드
     *
     * @param authentication 사용자 인증 정보를 담고 있는 Authentication 객체
     * @param userIdx        사용자의 고유 ID.
     * @return 생성된 JWT 리프레시 토큰
     */
    public String createRefreshToken(Authentication authentication, UUID userIdx) {
        return Jwts.builder()
                .subject(authentication.getName()) // sub 클레임: 토큰 제목을 지정
                .issuer("OnLog-SERVER") // iss 클레임: 토큰 발급자를 지정
                .issuedAt(new Date()) // iat 클레임: 토큰 발급 시간을 지정
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME)) // exp 클레임: 토큰 만료 시간을 지정
                .claim("client-id", userIdx) // 사용자 정의 클레임: 사용자의 식별자
                .signWith(secretKey) // signWith 메소드를 사용해 서명 알고리즘과 키를 지정
                .compact(); // JWT 토큰 문자열을 생성
    }

}
