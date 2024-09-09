package my.SimplyPosting.dto.user;

import lombok.Data;

@Data
public class UserPrivateDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
