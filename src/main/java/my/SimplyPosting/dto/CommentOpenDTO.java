package my.SimplyPosting.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentOpenDTO {
    private Long id;
    private Long authorId;
    private Long postId;
    private String content;
    private LocalDate createdAt;
    private Boolean deleted;
}
