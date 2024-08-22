package my.SimplyPosting.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

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
    UserModel author;

    @Column(length = 256)
    private String title;

    @Column(length = 64, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDate createdAt;
}
