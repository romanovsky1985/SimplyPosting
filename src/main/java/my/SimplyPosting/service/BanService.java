package my.SimplyPosting.service;

import my.SimplyPosting.dto.ban.BanCreateDTO;
import my.SimplyPosting.dto.ban.BanFilterDTO;
import my.SimplyPosting.dto.ban.BanOpenDTO;
import my.SimplyPosting.mapper.BanMapper;
import my.SimplyPosting.model.BanModel;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.repository.BanRepository;
import my.SimplyPosting.specification.BanSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
    @Autowired
    private BanSpecification banSpecification;

    public Page<BanOpenDTO> getPageByFilter(PageRequest pageRequest, BanFilterDTO filterDTO) {
        Specification<BanModel> specification = banSpecification.build(filterDTO);
        Page<BanModel> bans = banRepository.findAll(specification, pageRequest);
        return bans.map(banMapper::map);
    }

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
