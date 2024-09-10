package my.SimplyPosting.service;

import my.SimplyPosting.dto.comment.CommentCreateDTO;
import my.SimplyPosting.dto.comment.CommentFilterDTO;
import my.SimplyPosting.dto.comment.CommentOpenDTO;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.mapper.CommentMapper;
import my.SimplyPosting.model.CommentModel;
import my.SimplyPosting.repository.CommentRepository;
import my.SimplyPosting.specification.CommentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentSpecification commentSpecification;

    public CommentOpenDTO getById(Long id) {
        CommentModel comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment with id: " + id + " not found"));
        return commentMapper.map(comment);
    }

    public CommentOpenDTO create(CommentCreateDTO createDTO) {
        CommentModel comment = commentMapper.map(createDTO);
        commentRepository.save(comment);
        return commentMapper.map(comment);
    }

    public Page<CommentOpenDTO> getPageByFilter(PageRequest pageRequest, CommentFilterDTO filterDTO) {
        Specification<CommentModel> specification = commentSpecification.build(filterDTO);
        Page<CommentModel> comments = commentRepository.findAll(specification, pageRequest);
        return comments.map(commentMapper::map);
    }

    public void setDeletedById(Long id) {
        CommentModel comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment with id: " + id + " not found"));
        comment.setDeleted(true);
        commentRepository.save(comment);
    }


}
