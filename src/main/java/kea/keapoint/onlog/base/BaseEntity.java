package kea.keapoint.onlog.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity {

    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)// 기본값 지정
    @CreationTimestamp
    private LocalDateTime createdAt; // Row 생성 시점

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt; // Row 업데이트 시점

    @JsonIgnore
    @Column(name = "status", nullable = false)
    private Boolean status = true; // Row 유효 상태

    public BaseEntity(LocalDateTime createdAt, LocalDateTime updatedAt, Boolean status) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
