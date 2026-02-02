package bxnd.sori.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmittedAssignment extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long assignment_id;

    private String title;
    private String content;
    private boolean date_over_flag;
    private boolean checked_flag;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author; // Member 엔티티 자체를 필드로 가짐
}
