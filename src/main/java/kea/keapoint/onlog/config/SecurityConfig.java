package kea.keapoint.onlog.config;

import kea.keapoint.onlog.oauth.handler.OAuth2LoginFailureHandler;
import kea.keapoint.onlog.oauth.handler.OAuth2LoginSuccessHandler;
import kea.keapoint.onlog.oauth.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정 클래스
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            CustomOAuth2UserService customOAuth2UserService,
            OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler,
            OAuth2LoginFailureHandler oAuth2LoginFailureHandler
    ) throws Exception {

        return http
                .httpBasic(AbstractHttpConfigurer::disable) // rest api 이므로 기본 설정 사용 안 함. 기본 설정은 비인증 시 로그인폼 화면으로 리다이렉트 된다.
                .csrf(AbstractHttpConfigurer::disable) // rest api이므로 csrf 보안이 필요 없으므로 disable 처리.
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증 -> 세션은 필요 없으므로 생성 안 함
                )
                .cors(AbstractHttpConfigurer::disable) // CORS(Cross-Origin Resource Sharing) 설정 비활성화
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) // X-Frame-Options 비활성화 (IFrame 사용 가능하도록)
                )
                .formLogin(AbstractHttpConfigurer::disable) // formLogin 대신 Jwt를 사용하기 때문에 disable로 설정
                .logout(AbstractHttpConfigurer::disable) // 로그아웃 기능 비활성화
                .authorizeHttpRequests(
                        request -> request
                                .anyRequest().permitAll() // 임시) 모든 요청이 인증이 필요 없도록 설정
                )
                .oauth2Login(
                        oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                                .userInfoEndpoint(
                                        userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService)
                                )
                                .successHandler(oAuth2LoginSuccessHandler) // OAuth2 로그인 성공 핸들러
                                .failureHandler(oAuth2LoginFailureHandler) // OAuth2 로그인 실패 핸들러
                )
                .build();
    }
}
