package my.SimplyPosting.controller;

import my.SimplyPosting.dto.CommentCreateDTO;
import my.SimplyPosting.dto.CommentFilterDTO;
import my.SimplyPosting.dto.CommentOpenDTO;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentOpenDTO> show(@PathVariable Long id) {
        CommentOpenDTO comment = commentService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(comment);
    }

    @GetMapping(path = "")
    public ResponseEntity<Page<CommentOpenDTO>> find(@RequestBody CommentFilterDTO filterDTO,
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<CommentOpenDTO> comments = commentService.getPageByFilter(pageRequest, filterDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Count", String.valueOf(comments.getSize()))
                .body(comments);
    }

    @PostMapping(path = "")
    public ResponseEntity<CommentOpenDTO> create(@RequestBody @Validated CommentCreateDTO createDTO) {
        CommentOpenDTO comment = commentService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comment);
    }

    @DeleteMapping(path = "/{id]")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            commentService.setDeletedById(id);
        } catch (ResourceNotFoundException ignored) {}
    }
}
