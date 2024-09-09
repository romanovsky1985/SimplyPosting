package my.SimplyPosting.service;

import my.SimplyPosting.dto.ban.BanCreateDTO;
import my.SimplyPosting.dto.ban.BanOpenDTO;
import my.SimplyPosting.mapper.BanMapper;
import my.SimplyPosting.model.BanModel;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.repository.BanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BanService {
    @Autowired
    private BanRepository banRepository;
    @Autowired
    private BanMapper banMapper;
    @Autowired
    private UserService userService;

    public BanOpenDTO create(BanCreateDTO createDTO) {
        UserModel moderator = userService.getAuthenticatedUser();
        BanModel ban = banMapper.map(createDTO);
        ban.setModerator(moderator);
        userService.setBanById(createDTO.getOffenderId(),
                LocalDateTime.now().plusDays(createDTO.getDays()));
        banRepository.save(ban);
        return banMapper.map(ban);
    }
}
