package my.SimplyPosting.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
@EntityListeners(AuditingEntityListener.class)
public class PostModel implements BaseEntity {

    //////////////////// Base Fields ////////////////////

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserModel author;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String summary;

    @CreatedDate
    private LocalDateTime createdAt;

    private Boolean deleted;
}
