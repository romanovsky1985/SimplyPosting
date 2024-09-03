package my.SimplyPosting.mapper;

import my.SimplyPosting.dto.PostContentDTO;
import my.SimplyPosting.dto.PostCreateDTO;
import my.SimplyPosting.dto.PostOpenDTO;
import my.SimplyPosting.exception.PermissionDeniedException;
import my.SimplyPosting.model.PostModel;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.repository.UserRepository;
import my.SimplyPosting.service.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    @AfterMapping
    public void setCurrentAuthor(PostModel model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel author = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new PermissionDeniedException("Only authenticated user can create post!"));
        model.setAuthor(author);
    }

    @Mapping(target = "authorId", source = "author.id")
    public abstract PostOpenDTO map(PostModel model);
    @AfterMapping
    public void deleteData(PostOpenDTO openDTO) {
        if (openDTO.getDeleted()) {
            openDTO.setTitle("");
            openDTO.setSummary("");
        }
    }

    public abstract PostContentDTO content(PostModel model);
    @AfterMapping
    public void deleteContent(PostContentDTO contentDTO) {
        if (contentDTO.getDeleted()) {
            contentDTO.setContent("");
        }
    }
}
