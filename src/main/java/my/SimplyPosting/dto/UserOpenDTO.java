package my.SimplyPosting.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserOpenDTO {

    private long id;

    private String role;

    private String username;

    boolean deleted;

    private LocalDate createdAt;
}
