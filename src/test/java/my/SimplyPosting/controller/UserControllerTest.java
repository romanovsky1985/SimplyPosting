package my.SimplyPosting.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.dto.UserOpenDTO;
import my.SimplyPosting.service.UserService;
import my.SimplyPosting.utils.CreateDTOFaker;
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

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private final String controllerUrl = "/api/users";

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CreateDTOFaker faker;

    @Test
    public void testShow() throws Exception {
        UserOpenDTO openDTO = userService.create(faker.fakeCreateUserDTO());
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(controllerUrl + "/" + openDTO.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        UserOpenDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<UserOpenDTO>() {});
        Assertions.assertEquals(openDTO.getUsername(), responseDTO.getUsername());
    }

    @Test
    public void testCreate() throws Exception {
        UserCreateDTO createDTO = faker.fakeCreateUserDTO();
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(controllerUrl)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        UserOpenDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<UserOpenDTO>() {});
        Long id = responseDTO.getId();
        UserOpenDTO openDTO = userService.getById(id);
        Assertions.assertEquals(createDTO.getUsername(), openDTO.getUsername());
    }

    @Test
    public void testDelete() throws Exception {
        UserOpenDTO openDTO = userService.create(faker.fakeCreateUserDTO());
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete(controllerUrl + "/" + openDTO.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
        UserOpenDTO deletedDTO = userService.getById(openDTO.getId());
        Assertions.assertTrue(deletedDTO.getDeleted());
        Assertions.assertNull(deletedDTO.getUsername());
    }
}
