package my.SimplyPosting.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateDTO {

    private Long authorId;

    private Long postId;

    @NotBlank
    private String content;
}
