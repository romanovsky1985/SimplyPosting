package my.SimplyPosting.dto.ban;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BanFilterDTO {
    private Long moderatorId;
    private Long offenderId;
    private String InfoContain;
    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
}