package my.SimplyPosting.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostOpenDTO {
    private Long id;
    private Long authorId;
    private String title;
    private String summary;
    private LocalDateTime createdAt;
    private Boolean deleted;
}
