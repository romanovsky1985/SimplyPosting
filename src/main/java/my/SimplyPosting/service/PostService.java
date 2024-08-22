package my.SimplyPosting.service;

import my.SimplyPosting.dto.PostContentDTO;
import my.SimplyPosting.dto.PostCreateDTO;
import my.SimplyPosting.dto.PostOpenDTO;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.mapper.PostMapper;
import my.SimplyPosting.model.PostModel;
import my.SimplyPosting.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;

    public List<PostOpenDTO> getAll() {
        return postRepository.findAll().stream()
                .map(postMapper::map)
                .toList();
    }

    public PostOpenDTO getById(Long id) {
        PostModel post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post id: " + id + " not found"));
        return postMapper.map(post);
    }

    public List<PostContentDTO> getContentAll() {
        return postRepository.findAll().stream()
                .map(postMapper::content)
                .toList();
    }

    public PostContentDTO getContentById(Long id) {
        PostModel post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post id: " + id + " not found"));
        return postMapper.content(post);
    }

    public PostOpenDTO create(PostCreateDTO createDTO) {
        PostModel post = postMapper.map(createDTO);
        postRepository.save(post);
        return postMapper.map(post);
    }
}
