package my.SimplyPosting.controller;

import my.SimplyPosting.dto.ban.BanCreateDTO;
import my.SimplyPosting.dto.ban.BanOpenDTO;
import my.SimplyPosting.service.BanService;
import my.SimplyPosting.utils.Routing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BanController {
    @Autowired
    private BanService banService;

    @PostMapping(path = Routing.BANS)
    public ResponseEntity<BanOpenDTO> create(@RequestBody @Validated BanCreateDTO createDTO) {
        BanOpenDTO ban = banService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ban);
    }
}
