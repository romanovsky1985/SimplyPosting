package my.SimplyPosting.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "bans")
@Data
@EntityListeners(AuditingEntityListener.class)
public class BanModel implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer days;

    private String info;

    @ManyToOne
    private UserModel moderator;

    @ManyToOne
    private UserModel offender;

    @ManyToOne
    private PostModel byPost;

    @ManyToOne
    private CommentModel byComment;

    @CreatedDate
    private LocalDateTime createdAt;
}
