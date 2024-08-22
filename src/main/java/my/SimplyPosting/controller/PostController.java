package my.SimplyPosting.controller;

import my.SimplyPosting.dto.PostContentDTO;
import my.SimplyPosting.dto.PostCreateDTO;
import my.SimplyPosting.dto.PostOpenDTO;
import my.SimplyPosting.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping(path = "")
    public ResponseEntity<List<PostOpenDTO>> findPost() {
        List<PostOpenDTO> posts = postService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostOpenDTO> showPost(@PathVariable Long id) {
        PostOpenDTO post = postService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }

    @GetMapping(path = "/content")
    public ResponseEntity<List<PostContentDTO>> findContent() {
        List<PostContentDTO> posts = postService.getContentAll();
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @GetMapping(path = "/content/{id}")
    public ResponseEntity<PostContentDTO> showContent(@PathVariable Long id) {
        PostContentDTO post = postService.getContentById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }

    @PostMapping(path = "")
    public ResponseEntity<PostOpenDTO> create(@RequestBody @Validated PostCreateDTO createDTO) {
        PostOpenDTO post = postService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(post);
    }
}
