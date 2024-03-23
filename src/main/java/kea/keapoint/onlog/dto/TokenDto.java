package kea.keapoint.onlog.dto;

import kea.keapoint.onlog.base.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TokenDto {
    private Role role;
    private String accessToken;
    private String refreshToken;
}
