package kea.keapoint.onlog.dto;

import kea.keapoint.onlog.base.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TokenDto {
    private Role role; // 사용자 권한
    private String accessToken; // 액세스 토큰
    private String refreshToken; // 리프레시 토큰
}
