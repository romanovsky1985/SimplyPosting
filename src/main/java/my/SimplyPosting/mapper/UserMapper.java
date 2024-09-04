package my.SimplyPosting.mapper;

import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.dto.UserOpenDTO;
import my.SimplyPosting.dto.UserUpdateDTO;
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
    public void setDefaults(UserModel model) {
        if (model.getRole() == null) {
            model.setRole("USER");
        }
        model.setBannedBefore(LocalDateTime.MIN);
    }

    public abstract UserOpenDTO map(UserModel model);

    @BeforeMapping
    public void encodePassword(UserUpdateDTO updateDTO) {
        if (updateDTO.getPassword() != null) {
            String password = updateDTO.getPassword().get();
            updateDTO.setPassword(JsonNullable.of(passwordEncoder.encode(password)));
        }
    }
    @Mapping(target = "cryptPassword", source = "password")
    public abstract void update(UserUpdateDTO updateDTO, @MappingTarget UserModel model);

}
