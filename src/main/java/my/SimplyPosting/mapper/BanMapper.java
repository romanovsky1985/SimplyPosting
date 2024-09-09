package my.SimplyPosting.mapper;

import my.SimplyPosting.dto.ban.BanCreateDTO;
import my.SimplyPosting.dto.ban.BanOpenDTO;
import my.SimplyPosting.model.BanModel;
import org.mapstruct.*;

import java.lang.annotation.Target;

@Mapper(
        uses = {ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class BanMapper {
    @Mapping(target = "offender", source = "offenderId")
    @Mapping(target = "byPost", source = "byPostId")
    @Mapping(target = "byComment", source = "byCommentId")
    public abstract BanModel map(BanCreateDTO createDTO);

    @Mapping(target = "moderatorId", source = "moderator.id")
    @Mapping(target = "offenderId", source = "offender.id")
    @Mapping(target = "byPostId", source = "byPost.id")
    @Mapping(target = "byCommentId", source = "byComment.id")
    public abstract BanOpenDTO map(BanModel model);
}
