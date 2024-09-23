package my.SimplyPosting.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.SimplyPosting.dto.ban.BanCreateDTO;
import my.SimplyPosting.dto.ban.BanOpenDTO;
import my.SimplyPosting.dto.user.UserCreateDTO;
import my.SimplyPosting.dto.user.UserOpenDTO;
import my.SimplyPosting.service.BanService;
import my.SimplyPosting.service.UserService;
import my.SimplyPosting.utils.CreateDTOFaker;
import my.SimplyPosting.utils.Routing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class BanControllerTest {
    private final String controllerUrl = Routing.BANS;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CreateDTOFaker faker;
    @Autowired
    private BanService banService;
    @Autowired
    private UserService userService;

    @Test
    public void testCreate() throws Exception {
        UserOpenDTO offender = userService.create(faker.fakeCreateUserDTO());
        UserOpenDTO moderator = userService.create(faker.fakeCreateUserDTO());
        BanCreateDTO banCreateDTO = faker.fakeBanCreateDTO(offender.getId());
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(controllerUrl)
                        .with(SecurityMockMvcRequestPostProcessors.user(moderator.getUsername())
                                .authorities(new SimpleGrantedAuthority("MODERATOR")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(banCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        BanOpenDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<BanOpenDTO>() {});
        Assertions.assertEquals(LocalDateTime.now().getDayOfYear(),
                userService.getById(offender.getId()).getBannedBefore().minusDays(banCreateDTO.getDays()).getDayOfYear());
        Assertions.assertEquals(responseDTO.getModeratorId(), moderator.getId());
        Assertions.assertEquals(responseDTO.getOffenderId(), offender.getId());
    }
}
