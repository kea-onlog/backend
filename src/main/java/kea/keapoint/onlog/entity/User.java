package kea.keapoint.onlog.entity;

import jakarta.persistence.*;
import kea.keapoint.onlog.base.BaseEntity;
import kea.keapoint.onlog.base.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String profileImage;

    @Column
    private String registrationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String profileImage, String registrationId, Role role) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.registrationId = registrationId;
        this.role = role;
    }

    public User updateProfile(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
        return this;
    }

    public void authorize() {
        this.role = Role.USER;
    }
}
