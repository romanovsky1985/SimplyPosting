package my.SimplyPosting.component;

import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {
    @Autowired
    UserService userService;

    public void run(ApplicationArguments args) {
        UserCreateDTO admin = new UserCreateDTO("admin", "FirstName", "LastName", "admin@nomail.ru", "qwerty", "ADMIN");
        userService.create(admin);
    }
}
