package my.SimplyPosting.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserFilterDTO {
    private String usernameContains;
    private LocalDate createdAfter;
    private LocalDate createdBefore;
    private Boolean deletedStatus;
    private Boolean bannedStatus;
}
