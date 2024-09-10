package my.SimplyPosting.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateDTO {

    private Long postId;

    @NotBlank
    private String content;
}
