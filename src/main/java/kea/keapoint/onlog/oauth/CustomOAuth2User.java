package kea.keapoint.onlog.oauth;

import kea.keapoint.onlog.base.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    private UUID userIdx; // 사용자 식별자
    private String email; // 사용자 이메일
    private Role role; // 사용자 역할

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            UUID userIdx, String email, Role role) {

        super(authorities, attributes, nameAttributeKey);
        this.userIdx = userIdx;
        this.email = email;
        this.role = role;
    }
}
