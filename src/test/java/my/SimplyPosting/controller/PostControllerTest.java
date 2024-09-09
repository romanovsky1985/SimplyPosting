package my.SimplyPosting.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.SimplyPosting.dto.post.PostCreateDTO;
import my.SimplyPosting.dto.post.PostOpenDTO;
import my.SimplyPosting.dto.user.UserOpenDTO;
import my.SimplyPosting.service.PostService;
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
public class PostControllerTest {
    private final String controllerUrl = "/api/posts";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CreateDTOFaker faker;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Test
    public void testCreate() throws Exception {
        UserOpenDTO author = userService.create(faker.fakeCreateUserDTO());
        PostCreateDTO postCreateDTO = faker.fakePostCreateDTO();
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(controllerUrl)
                        .with(SecurityMockMvcRequestPostProcessors.user(author.getUsername()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        PostOpenDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<PostOpenDTO>() {});
        Assertions.assertEquals(postCreateDTO.getTitle(), responseDTO.getTitle());
        Assertions.assertEquals(author.getId(), responseDTO.getAuthorId());
        Assertions.assertEquals(postCreateDTO.getContent(),
                postService.getContentById(responseDTO.getId()).getContent());
    }

    @Test
    public void testDelete() throws Exception {
        UserOpenDTO author = userService.create(faker.fakeCreateUserDTO());
        PostCreateDTO postCreateDTO = faker.fakePostCreateDTO();
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(controllerUrl)
                        .with(SecurityMockMvcRequestPostProcessors.user(author.getUsername()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        PostOpenDTO postOpenDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<PostOpenDTO>() {});
        mockMvc
                .perform(MockMvcRequestBuilders.delete(controllerUrl + "/" + postOpenDTO.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("moderator")
                                .authorities(new SimpleGrantedAuthority("MODERATOR"))))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Assertions.assertTrue(postService.getById(postOpenDTO.getId()).getDeleted());
        Assertions.assertEquals(postService.getContentById(postOpenDTO.getId()).getContent(), "");
    }
}
