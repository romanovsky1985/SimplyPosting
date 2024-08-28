package my.SimplyPosting.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostFilterDTO {
    private Long authorId;
    private String titleContain;
    private String summaryContain;
    private LocalDate createdAfter;
    private LocalDate createdBefore;
    private Boolean deletedStatus;
}
