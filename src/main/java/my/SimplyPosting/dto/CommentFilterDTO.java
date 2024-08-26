package my.SimplyPosting.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentFilterDTO {
    private Long authorId;
    private Long postId;
    private LocalDate createdAfter;
    private LocalDate createdBefore;
    private Boolean deletedStatus;
}
