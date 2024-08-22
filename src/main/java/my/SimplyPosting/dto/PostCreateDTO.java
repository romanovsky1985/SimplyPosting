package my.SimplyPosting.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDTO {

    private Long authorId;

    @NotBlank
    private String title;

    @NotBlank
    private String slug;

    @NotBlank
    private String content;
}
