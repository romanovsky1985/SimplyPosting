package my.SimplyPosting.component;

import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.repository.UserRepository;
import my.SimplyPosting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements ApplicationRunner {
    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void run(ApplicationArguments args) {
        UserModel admin = new UserModel();
        admin.setUsername("admin");
        admin.setRole("ADMIN");
        admin.setCryptPassword(passwordEncoder.encode("qwerty"));
        userRepository.save(admin);
    }
}
