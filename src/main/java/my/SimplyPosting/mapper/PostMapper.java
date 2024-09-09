package my.SimplyPosting.mapper;

import my.SimplyPosting.dto.post.PostContentDTO;
import my.SimplyPosting.dto.post.PostCreateDTO;
import my.SimplyPosting.dto.post.PostOpenDTO;
import my.SimplyPosting.model.PostModel;
import my.SimplyPosting.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        uses = {ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public abstract class PostMapper {
    @Autowired
    private UserRepository userRepository;

    public abstract PostModel map(PostCreateDTO createDTO);

    @Mapping(target = "authorId", source = "author.id")
    public abstract PostOpenDTO map(PostModel model);
    @AfterMapping
    public void deleteData(PostModel model, @MappingTarget PostOpenDTO openDTO) {
        if (model.getDeleted()) {
            openDTO.setTitle("");
            openDTO.setSummary("");
        }
    }

    public abstract PostContentDTO content(PostModel model);
    @AfterMapping
    public void deleteContent(PostModel model, @MappingTarget PostContentDTO contentDTO) {
        if (model.getDeleted()) {
            contentDTO.setContent("");
        }
    }
}
