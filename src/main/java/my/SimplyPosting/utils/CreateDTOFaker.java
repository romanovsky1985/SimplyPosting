package my.SimplyPosting.utils;

import my.SimplyPosting.dto.UserCreateDTO;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;
import net.datafaker.providers.entertainment.GameOfThrones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateDTOFaker {

    private final Faker faker = new Faker();

    public UserCreateDTO fakeCreateUserDTO() {
        UserCreateDTO createDTO = new UserCreateDTO();
        createDTO.setUsername(faker.internet().username());
        createDTO.setFirstName(faker.name().firstName());
        createDTO.setLastName(faker.name().lastName());
        createDTO.setEmail(faker.internet().emailAddress());
        createDTO.setPassword(faker.text().text(6, 10, true));
        createDTO.setRole("USER");
        return createDTO;
    }
}
