package kea.keapoint.onlog.oauth;

import kea.keapoint.onlog.base.Role;
import kea.keapoint.onlog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보
    private String registrationId; // OAuth2 로그인을 처리한 서비스 명
    private String nameAttributesKey; // OAuth2 로그인 시 키 값
    private String name; // 유저 이름
    private String email; // 유저 이메일
    private String profileImageUrl; // 유저 프로필 이미지 URL

    /**
     * 소셜 서비스에 따라 적절한 OAuthAttributes 객체를 생성한다.
     *
     * @param registrationId        OAuth2 로그인을 처리한 서비스 명
     * @param userNameAttributeName OAuth2 로그인 시 키 값
     * @param attributes            유저 정보가 담긴 Map 객체
     * @return OAuthAttributes 객체
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "google" -> ofGoogle(userNameAttributeName, attributes);
            case "kakao" -> ofKakao(userNameAttributeName, attributes);
            default -> null;
        };
    }

    /**
     * Google 소셜 로그인에서 반환된 유저 정보를 바탕으로 OAuthAttributes 객체를 생성한다.
     *
     * @param userNameAttributeName OAuth2 로그인 시 키 값
     * @param attributes            유저 정보가 담긴 Map 객체
     * @return OAuthAttributes 객체
     */
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .attributes(attributes)
                .registrationId("google")
                .nameAttributesKey(userNameAttributeName)
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .profileImageUrl(String.valueOf(attributes.get("picture")))
                .build();
    }

    /**
     * Kakao 소셜 로그인에서 반환된 유저 정보를 바탕으로 OAuthAttributes 객체를 생성한다.
     *
     * @param userNameAttributeName OAuth2 로그인 시 키 값
     * @param attributes            유저 정보가 담긴 Map 객체
     * @return OAuthAttributes 객체
     */
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .attributes(attributes)
                .registrationId("kakao")
                .nameAttributesKey(userNameAttributeName)
                .name(String.valueOf(kakaoProfile.get("nickname")))
                .email(String.valueOf(kakaoAccount.get("email")))
                .profileImageUrl(String.valueOf(kakaoProfile.get("profile_image_url")))
                .build();
    }

    /**
     * OAuthAttributes 객체를 바탕으로 애플리케이션 내 사용자 엔티티를 생성한다.
     *
     * @return User 사용자 엔티티 객체
     */
    public User toEntity() {
        return User.builder()
                .registrationId(registrationId)
                .name(name)
                .email(email)
                .role(Role.GUEST)
                .profileImage(profileImageUrl)
                .build();
    }
}
