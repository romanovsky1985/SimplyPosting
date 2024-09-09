package my.SimplyPosting.mapper;

import my.SimplyPosting.dto.user.*;
import my.SimplyPosting.model.UserModel;
import org.mapstruct.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeMapping
    public void encodePassword(UserCreateDTO createDTO) {
        String password = createDTO.getPassword();
        createDTO.setPassword(passwordEncoder.encode(password));

    }
    @Mapping(target = "cryptPassword", source = "password")
    public abstract UserModel map(UserCreateDTO createDTO);
    @AfterMapping
    public void setDefaults(UserCreateDTO createDTO, @MappingTarget UserModel model) {
        if (createDTO.getRole() == null) {
            model.setRole("USER");
        }
        model.setBannedBefore(LocalDateTime.now());
    }


    public abstract UserOpenDTO map(UserModel model);

    public abstract UserPrivateDTO mapToPrivate(UserModel model);

    @BeforeMapping
    public void encodePassword(UserUpdateDTO updateDTO) {
        if (updateDTO.getPassword() != null) {
            String password = updateDTO.getPassword().get();
            updateDTO.setPassword(JsonNullable.of(passwordEncoder.encode(password)));
        }
    }
    @Mapping(target = "cryptPassword", source = "password")
    public abstract void update(UserUpdateDTO updateDTO, @MappingTarget UserModel model);

    public abstract void modify(UserModificationDTO modificationDTO, @MappingTarget UserModel model);

}
