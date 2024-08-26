package my.SimplyPosting.controller;

import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.dto.UserOpenDTO;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "")
    public ResponseEntity<List<UserOpenDTO>> index(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                   @RequestParam(defaultValue = "10") Integer pageLength) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageLength);
        List<UserOpenDTO> users = userService.getAllExisting(pageRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(users);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserOpenDTO> show(@PathVariable Long id) {
        UserOpenDTO user = userService.getById(id);
        return ResponseEntity.status((HttpStatus.OK))
                .body(user);
    }

    @PostMapping(path = "")
    public ResponseEntity<UserOpenDTO> create(@RequestBody @Validated UserCreateDTO createDTO) {
        UserOpenDTO user = userService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            userService.setDeletedById(id);
        } catch (ResourceNotFoundException ignored) {};
    }

}
