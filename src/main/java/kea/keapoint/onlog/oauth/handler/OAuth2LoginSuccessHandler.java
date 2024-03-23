package kea.keapoint.onlog.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kea.keapoint.onlog.base.Role;
import kea.keapoint.onlog.dto.TokenDto;
import kea.keapoint.onlog.entity.User;
import kea.keapoint.onlog.oauth.CustomOAuth2User;
import kea.keapoint.onlog.repository.UserRepository;
import kea.keapoint.onlog.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    private static final String BASE_URL = "http://localhost:3000";

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // 처음 요청한 회원이라면 사용자가 만들 블로그 정보를 추가로 입력 받아야 하므로 추가 정보를 입력 받는 페이지로 리다이렉트한다.
            if (oAuth2User.getRole().equals(Role.GUEST)) {
                // 사용자의 access token을 생성한다.
                String accessToken = jwtTokenProvider.createAccessToken(authentication, oAuth2User.getUserIdx());

                // 쿠키를 생성한다.
                Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
                accessTokenCookie.setHttpOnly(true);
                accessTokenCookie.setMaxAge(60 * 5); // 5분
                accessTokenCookie.setPath("/");
                accessTokenCookie.setSecure(true);

                // 쿠키를 응답에 추가한다.
                response.addCookie(accessTokenCookie);

                // 사용자의 Role을 USER로 변경한다.
                userRepository.findById(oAuth2User.getUserIdx()).ifPresent(User::authorize);

                // 블로그 정보를 추가로 입력 받는 페이지로 리다이렉트한다.
                getRedirectStrategy().sendRedirect(request, response, BASE_URL + "/signup");

            } else {
                // 기존 사용자라면 사용자의 token을 발급한다.
                TokenDto tokens = jwtTokenProvider.createTokens(authentication, oAuth2User.getUserIdx());

                // 쿠키를 생성한다.
                Cookie accessTokenCookie = new Cookie("accessToken", tokens.getAccessToken());
                accessTokenCookie.setHttpOnly(true);
                accessTokenCookie.setMaxAge(60 * 5); // 5분
                accessTokenCookie.setPath("/");
                accessTokenCookie.setSecure(true);

                Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.getRefreshToken());
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setMaxAge(60 * 5); // 5분
                refreshTokenCookie.setPath("/");
                refreshTokenCookie.setSecure(true);

                // 쿠키를 응답에 추가한다.
                response.addCookie(accessTokenCookie);
                response.addCookie(refreshTokenCookie);

                // 메인 페이지로 리다이렉트한다.
                getRedirectStrategy().sendRedirect(request, response, BASE_URL + "/main");
            }

        } catch (Exception e) {
            throw e;
        }

    }

}
