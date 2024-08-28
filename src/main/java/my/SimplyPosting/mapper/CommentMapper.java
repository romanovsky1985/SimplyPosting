package my.SimplyPosting.mapper;

import my.SimplyPosting.dto.CommentCreateDTO;
import my.SimplyPosting.dto.CommentOpenDTO;
import my.SimplyPosting.model.CommentModel;
import org.mapstruct.*;

@Mapper(
        uses = {ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public abstract class CommentMapper {
    @Mapping(target = "author", source = "authorId")
    @Mapping(target = "post", source = "postId")
    public abstract CommentModel map(CommentCreateDTO createDTO);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "postId", source = "post.id")
    public abstract CommentOpenDTO map(CommentModel model);
    @AfterMapping
    public void deleteContent(CommentOpenDTO openDTO) {
        if (openDTO.getDeleted()) {
            openDTO.setContent("");
        }
    }
}
