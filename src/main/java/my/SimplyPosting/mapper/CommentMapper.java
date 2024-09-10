package my.SimplyPosting.mapper;

import my.SimplyPosting.dto.comment.CommentCreateDTO;
import my.SimplyPosting.dto.comment.CommentOpenDTO;
import my.SimplyPosting.exception.PermissionDeniedException;
import my.SimplyPosting.model.CommentModel;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.repository.UserRepository;
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

public abstract class CommentMapper {
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "post", source = "postId")
    public abstract CommentModel map(CommentCreateDTO createDTO);
    @AfterMapping
    public void setCurrentAuthor(CommentModel model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel author = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new PermissionDeniedException("Only authenticated user can create comment!"));
        model.setAuthor(author);
    }

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
