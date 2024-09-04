package my.SimplyPosting.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.dto.UserOpenDTO;
import my.SimplyPosting.dto.UserUpdateDTO;
import my.SimplyPosting.service.UserService;
import my.SimplyPosting.utils.CreateDTOFaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
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

    @Test
    public void testUpdate() throws Exception {
        UserCreateDTO createDTO = faker.fakeCreateUserDTO();
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setOldPassword(createDTO.getPassword());
        updateDTO.setFirstName(JsonNullable.of(faker.fakeCreateUserDTO().getFirstName()));
        UserOpenDTO openDTO = userService.create(createDTO);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put(controllerUrl)
                        .with(SecurityMockMvcRequestPostProcessors.user(
                                userService.loadUserByUsername(openDTO.getUsername())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void testIsFreeUsername() throws Exception {
        String username = faker.fakeCreateUserDTO().getUsername();
        MvcResult freeResult = mockMvc
                .perform(MockMvcRequestBuilders.get(controllerUrl + "/is_free_username" )
                        .param("username", username))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertEquals("true", freeResult.getResponse().getContentAsString());
        MvcResult falseResult = mockMvc
                .perform(MockMvcRequestBuilders.get(controllerUrl + "/is_free_username")
                        .param("username", "admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertEquals("false", falseResult.getResponse().getContentAsString());
    }
}
