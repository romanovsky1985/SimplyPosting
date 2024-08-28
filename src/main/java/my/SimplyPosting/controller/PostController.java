package my.SimplyPosting.controller;

import my.SimplyPosting.dto.PostContentDTO;
import my.SimplyPosting.dto.PostCreateDTO;
import my.SimplyPosting.dto.PostFilterDTO;
import my.SimplyPosting.dto.PostOpenDTO;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    // получить пост по id
    @GetMapping(path = "/{id}")
    public ResponseEntity<PostOpenDTO> show(@PathVariable Long id) {
        PostOpenDTO post = postService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }

    // найти посты по фильтру с пагинацией
    @GetMapping(path = "")
    public ResponseEntity<Page<PostOpenDTO>> find(PostFilterDTO filterDTO,
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page -1, size);
        Page<PostOpenDTO> posts = postService.getPageByFilter(pageRequest, filterDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(posts);
    }

    // получить контент конкретного поста по id
    @GetMapping(path = "/content/{id}")
    public ResponseEntity<PostContentDTO> content(@PathVariable Long id) {
        PostContentDTO post = postService.getContentById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }

    // создать пост
    @PostMapping(path = "")
    public ResponseEntity<PostOpenDTO> create(@RequestBody @Validated PostCreateDTO createDTO) {
        PostOpenDTO post = postService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(post);
    }

    // удалить пост
    @DeleteMapping(path = "")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            postService.setDeletedById(id);
        } catch (ResourceNotFoundException ignored) {}
    }
}
