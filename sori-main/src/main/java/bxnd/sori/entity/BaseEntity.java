package bxnd.sori.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false, name = "created_at") // DB 컬럼명 명시
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at") // DB 컬럼명 명시
    private LocalDateTime updatedAt;
}