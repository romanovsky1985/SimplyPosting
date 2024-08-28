package my.SimplyPosting.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostOpenDTO {
    private Long id;
    private Long authorId;
    private String title;
    private String summary;
    private LocalDate createdAt;
    private Boolean deleted;
}
