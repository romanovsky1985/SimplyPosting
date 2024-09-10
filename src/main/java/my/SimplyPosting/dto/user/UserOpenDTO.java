package my.SimplyPosting.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserOpenDTO {
    private Long id;
    private String role;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime bannedBefore;
    private Boolean deleted;
}
