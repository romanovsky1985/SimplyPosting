package my.SimplyPosting.dto.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentOpenDTO {
    private Long id;
    private Long authorId;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean deleted;
}
