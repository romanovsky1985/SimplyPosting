package my.SimplyPosting.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Data
@EntityListeners(AuditingEntityListener.class)
public class CommentModel implements BaseEntity {

    /////////////////// Base Fields ////////////////////

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private  UserModel author;

    @ManyToOne
    private PostModel post;

    @Column(length = 1024)
    private String content;

    @CreatedDate
    private LocalDate createdAt;

    private Boolean deleted;
}
