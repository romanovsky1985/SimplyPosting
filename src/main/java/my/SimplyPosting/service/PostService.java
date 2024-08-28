package my.SimplyPosting.service;

import my.SimplyPosting.dto.*;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.mapper.PostMapper;
import my.SimplyPosting.model.PostModel;
import my.SimplyPosting.repository.PostRepository;
import my.SimplyPosting.specification.PostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostSpecification postSpecification;

    public PostOpenDTO getById(Long id) {
        PostModel post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post id: " + id + " not found"));
        return postMapper.map(post);
    }

    public PostContentDTO getContentById(Long id) {
        PostModel post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post id: " + id + " not found"));
        return postMapper.content(post);
    }

    public Page<PostOpenDTO> getPageByFilter(PageRequest pageRequest, PostFilterDTO filterDTO) {
        Specification<PostModel> specification = postSpecification.build(filterDTO);
        Page<PostModel> posts = postRepository.findAll(specification, pageRequest);
        return posts.map(postMapper::map);
    }

    public PostOpenDTO create(PostCreateDTO createDTO) {
        PostModel post = postMapper.map(createDTO);
        postRepository.save(post);
        return postMapper.map(post);
    }

    public void setDeletedById(Long id) {
        PostModel post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post id: " + id + " not found"));
        post.setDeleted(true);
        postRepository.save(post);
    }
}
