package my.SimplyPosting.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateDTO {

    private Long postId;

    @NotBlank
    private String content;
}
