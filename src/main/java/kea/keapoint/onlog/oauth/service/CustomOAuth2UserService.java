package kea.keapoint.onlog.oauth.service;

import kea.keapoint.onlog.entity.User;
import kea.keapoint.onlog.oauth.CustomOAuth2User;
import kea.keapoint.onlog.oauth.OAuthAttributes;
import kea.keapoint.onlog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest: {}", userRequest);

        // OAuth2 정보를 가져온다.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // OAuth2 로그인을 처리한 서비스 정보를 가져온다. (google, kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드값(Primary Key)을 가져온다.
        // 참고) 구글은 키 값은 "sub", 네이버는 "response", 카카오는 "id"다.
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2 서비스에서 가져온 유저 정보를 가져온다.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 사용자 정보를 저장하거나 업데이트한다.
        User user = saveOrUpdate(attributes, registrationId);

        // OAuth2 로그인 사용자 정보를 담는 객체를 생성 및 반환한다.
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getValue())),
                attributes.getAttributes(),
                attributes.getNameAttributesKey(),
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    /**
     * 사용자 정보를 저장하거나 업데이트하는 메소드.
     * <p>
     * 이미 존재하는 회원이면 사용자의 이름과 프로필 이미지를 업데이트하고,
     * 처음 가입하는 회원이면 User 테이블을 생성한다.
     * </p>
     *
     * @param authAttributes OAuth2 로그인 시 가져온 유저 정보
     */
    private User saveOrUpdate(OAuthAttributes authAttributes, String registrationId) {
        User user = userRepository.findByEmailAndRegistrationId(authAttributes.getEmail(), registrationId)
                .map(entity -> entity.updateProfile(authAttributes.getName(), authAttributes.getProfileImageUrl()))
                .orElse(authAttributes.toEntity());

        return userRepository.save(user);
    }
}
