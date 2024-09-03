package my.SimplyPosting.specification;

import my.SimplyPosting.dto.CommentFilterDTO;
import my.SimplyPosting.model.CommentModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CommentSpecification {

    public Specification<CommentModel> build(CommentFilterDTO filterDTO) {
        return withAuthorId(filterDTO.getAuthorId())
                .and(withPostId(filterDTO.getPostId()))
                .and(withContentContain(filterDTO.getContentContain()))
                .and(withCreatedAfter(filterDTO.getCreatedAfter()))
                .and(withCreatedBefore(filterDTO.getCreatedBefore()))
                .and(withDeletedStatus(filterDTO.getDeletedStatus()));
    }

    private Specification<CommentModel> withAuthorId(Long authorId) {
        return (root, query, criteriaBuilder) -> authorId == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("author").get("id"), authorId);
    }

    private Specification<CommentModel> withPostId(Long postId) {
        return (root, query, criteriaBuilder) -> postId == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("post").get("id"), postId);
    }

    private Specification<CommentModel> withContentContain(String contentContain) {
        return (root, query, criteriaBuilder) -> contentContain == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.like(root.get("content"), "%" + contentContain + "%");
    }

    private Specification<CommentModel> withCreatedAfter(LocalDateTime createdAfter) {
        return (root, query, criteriaBuilder) -> createdAfter == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.greaterThan(root.get("createdAt"), createdAfter);
    }

    private Specification<CommentModel> withCreatedBefore(LocalDateTime createdBefore) {
        return (root, query, criteriaBuilder) -> createdBefore == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.lessThan(root.get("createdAt"), createdBefore);
    }

    private Specification<CommentModel> withDeletedStatus(Boolean deletedStatus) {
        return (root, query, criteriaBuilder) -> deletedStatus == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("deleted"), deletedStatus);
    }
}
