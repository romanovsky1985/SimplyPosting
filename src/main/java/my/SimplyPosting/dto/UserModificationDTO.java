package my.SimplyPosting.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class UserModificationDTO {
    private Long id;
    @NotBlank
    private JsonNullable<String> username;
    @NotBlank
    @Email
    private JsonNullable<String> email;
    @NotBlank
    private JsonNullable<String> role;
}
