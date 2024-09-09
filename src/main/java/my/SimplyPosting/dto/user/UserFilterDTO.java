package my.SimplyPosting.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFilterDTO {
    private String usernameContains;
    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private Boolean deletedStatus;
    private Boolean bannedStatus;
}
