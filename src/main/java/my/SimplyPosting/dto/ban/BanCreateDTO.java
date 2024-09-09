package my.SimplyPosting.dto.ban;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanCreateDTO {
    @NotNull
    private Integer days;

    private String info;

    @NotNull
    private Long offenderId;

    private Long byPostId;

    private Long byCommentId;
}
