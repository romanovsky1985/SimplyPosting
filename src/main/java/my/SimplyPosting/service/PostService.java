package my.SimplyPosting.service;

import my.SimplyPosting.dto.post.PostContentDTO;
import my.SimplyPosting.dto.post.PostCreateDTO;
import my.SimplyPosting.dto.post.PostFilterDTO;
import my.SimplyPosting.dto.post.PostOpenDTO;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.mapper.PostMapper;
import my.SimplyPosting.model.PostModel;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.repository.PostRepository;
import my.SimplyPosting.specification.PostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostSpecification postSpecification;
    @Autowired
    private UserService userService;

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
        UserModel author = userService.getAuthenticatedUser();
        PostModel post = postMapper.map(createDTO);
        post.setDeleted(false);
        post.setAuthor(author);
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
