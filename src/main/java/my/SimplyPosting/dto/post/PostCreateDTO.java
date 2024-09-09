package my.SimplyPosting.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String summary;

    @NotBlank
    private String content;
}
