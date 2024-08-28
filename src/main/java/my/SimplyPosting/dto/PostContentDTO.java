package my.SimplyPosting.dto;

import lombok.Data;

@Data
public class PostContentDTO {
    private Long id;
    private String content;
    private Boolean deleted;
}
