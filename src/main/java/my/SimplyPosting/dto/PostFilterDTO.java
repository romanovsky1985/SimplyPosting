package my.SimplyPosting.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PostFilterDTO {
    private Long authorId;
    private String titleContain;
    private String summaryContain;
    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private Boolean deletedStatus;
}
