package my.SimplyPosting.mapper;

import my.SimplyPosting.dto.PostContentDTO;
import my.SimplyPosting.dto.PostCreateDTO;
import my.SimplyPosting.dto.PostOpenDTO;
import my.SimplyPosting.model.PostModel;
import org.mapstruct.*;

@Mapper(
        uses = {ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public abstract class PostMapper {
    @Mapping(target = "author", source = "authorId")
    public abstract PostModel map(PostCreateDTO createDTO);

    @Mapping(target = "authorId", source = "author.id")
    public abstract PostOpenDTO map(PostModel model);

    public abstract PostContentDTO content(PostModel model);
}
