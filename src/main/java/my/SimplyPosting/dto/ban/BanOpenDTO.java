package my.SimplyPosting.dto.ban;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BanOpenDTO {
    private Long id;
    private Integer days;
    private String info;
    private Long moderatorId;
    private Long offenderId;
    private Long byPostId;
    private Long byCommentId;
    private LocalDateTime createdAt;
}
