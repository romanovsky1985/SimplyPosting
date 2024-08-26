package my.SimplyPosting.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserOpenDTO {
    private Long id;
    private String role;
    private String username;
    private LocalDate createdAt;
    private Boolean deleted;
}
