package my.SimplyPosting.dto.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentFilterDTO {
    private Long authorId;
    private Long postId;
    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private String contentContain;
    private Boolean deletedStatus;
}
