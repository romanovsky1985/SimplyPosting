package my.SimplyPosting.controller;

import my.SimplyPosting.dto.user.*;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.service.UserService;
import my.SimplyPosting.utils.Routing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;


    // найти пользователей по фильтру с пагинацией
    @GetMapping(path = Routing.USERS)
    public ResponseEntity<Page<UserOpenDTO>> find(UserFilterDTO filterDTO,
                                                  @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<UserOpenDTO> users = userService.getPageByFilter(pageRequest, filterDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(users);
    }

    // получить пользователя по id
    @GetMapping(path = Routing.USERS_ID)
    public ResponseEntity<UserOpenDTO> show(@PathVariable Long id) {
        UserOpenDTO user = userService.getById(id);
        return ResponseEntity.status((HttpStatus.OK))
                .body(user);
    }

    // проверить свободный username
    @GetMapping(path = Routing.USERS_CHECK_USERNAME)
    public ResponseEntity<Boolean> isFreeUsername(String username) {
        Boolean isFree = userService.isFreeUsername(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(isFree);
    }

    // создать пользователя
    @PostMapping(path = Routing.USERS)
    public ResponseEntity<UserOpenDTO> create(@RequestBody @Validated UserCreateDTO createDTO) {
        UserOpenDTO user = userService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    // получить свои конфиденциальные данные
    @GetMapping(path = Routing.USERS_GET_PRIVATE)
    public ResponseEntity<UserPrivateDTO> showPrivate() {
        UserPrivateDTO user = userService.getPrivateByCurrentUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    // обновить свои конфиденциальные данные
    @PutMapping(path = Routing.USERS)
    public ResponseEntity<UserOpenDTO> update(@RequestBody @Validated UserUpdateDTO updateDTO) {
        UserOpenDTO user = userService.update(updateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    // обновить данные определяющие пользователя (логин, почта, роль)
    @PutMapping(path = Routing.USERS_MODIFICATION)
    public ResponseEntity<UserOpenDTO> modify(@RequestBody @Validated UserModificationDTO modificationDTO) {
        UserOpenDTO user = userService.modify(modificationDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    // удалить пользователя
    @DeleteMapping(path = Routing.USERS_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            userService.setDeletedById(id);
        } catch (ResourceNotFoundException ignored) {};
    }

}
