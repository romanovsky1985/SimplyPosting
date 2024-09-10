package my.SimplyPosting.utils;

import my.SimplyPosting.dto.ban.BanCreateDTO;
import my.SimplyPosting.dto.post.PostCreateDTO;
import my.SimplyPosting.dto.user.UserCreateDTO;
import net.datafaker.Faker;
import net.datafaker.providers.base.DcComics;
import net.datafaker.providers.base.Text;
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

    public PostCreateDTO fakePostCreateDTO() {
        PostCreateDTO createDTO = new PostCreateDTO();
        DcComics comics = faker.dcComics();
        createDTO.setTitle(comics.title());
        createDTO.setSummary(comics.hero() + " win " + comics.villain());
        createDTO.setContent("Comics story text...");
        return createDTO;
    }

    public BanCreateDTO fakeBanCreateDTO(Long offenderId) {
        BanCreateDTO createDTO = new BanCreateDTO();
        createDTO.setDays(faker.number().randomDigitNotZero());
        createDTO.setInfo(faker.futurama().hermesCatchPhrase());
        createDTO.setOffenderId(offenderId);
        return createDTO;
    }

}
