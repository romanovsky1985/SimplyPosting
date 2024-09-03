package my.SimplyPosting.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.dto.UserOpenDTO;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private final String controllerUrl = "/api/users";
    private final SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor token =
            SecurityMockMvcRequestPostProcessors.user("admin");
    private final UserCreateDTO testCreateDTO = new UserCreateDTO(
            "ivanov", "Ivan", "Ivanov", "ivanov@test.ru", "qwerty", "USER");

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testShow() throws Exception {
//        UserOpenDTO openDTO = userService.create(testCreateDTO);
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.get(controllerUrl + "/" + openDTO.getId())
//                        .with(token))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//        UserOpenDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
//                new TypeReference<UserOpenDTO>() {});
//        Assertions.assertEquals(openDTO, responseDTO);
//        userService.setDeletedById(openDTO.getId());
    }

    @Test
    public void testCreate() throws Exception {
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.post(controllerUrl)
//                        .with(token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(testCreateDTO)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andReturn();
//        UserOpenDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
//                new TypeReference<UserOpenDTO>() {});
//        UserOpenDTO openDTO = userService.getByUsername(testCreateDTO.getUsername());
//        Assertions.assertEquals(responseDTO, openDTO);
//        userService.setDeletedById(openDTO.getId());
    }

    @Test
    public void testDelete() throws Exception {
//        UserOpenDTO openDTO = userService.create(testCreateDTO);
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.delete(controllerUrl + "/" + openDTO.getId())
//                        .with(token))
//                .andExpect(MockMvcResultMatchers.status().isNoContent())
//                .andReturn();
//        UserOpenDTO deletedDTO = userService.getById(openDTO.getId());
//        Assertions.assertTrue(deletedDTO.getDeleted());
    }
}
