package my.SimplyPosting.specification;

import my.SimplyPosting.dto.post.PostFilterDTO;
import my.SimplyPosting.model.PostModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostSpecification {

    public Specification<PostModel> build(PostFilterDTO filterDTO) {
        return withAuthorId(filterDTO.getAuthorId())
                .and(withTitleContain(filterDTO.getTitleContain()))
                .and(withSummaryContain(filterDTO.getSummaryContain()))
                .and(withDeletedStatus(filterDTO.getDeletedStatus()))
                .and(withCreatedAfter(filterDTO.getCreatedAfter()))
                .and(withCreatedBefore(filterDTO.getCreatedBefore()));
    }

    private Specification<PostModel> withAuthorId(Long authorId) {
        return (root, query, criteriaBuilder) -> authorId == null ? criteriaBuilder.conjunction() :
            criteriaBuilder.equal(root.get("author").get("id"), authorId);
    }

    private Specification<PostModel> withTitleContain(String titleContain) {
        return (root, query, criteriaBuilder) -> titleContain == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.like(root.get("title"), "%" + titleContain + "%");
    }

    private Specification<PostModel> withSummaryContain(String summaryContain) {
        return (root, query, criteriaBuilder) -> summaryContain == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.like(root.get("summary"), "%s" + summaryContain + "%");

    }

    private Specification<PostModel> withCreatedAfter(LocalDateTime createdAfter) {
        return (root, query, criteriaBuilder) -> createdAfter == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.greaterThan(root.get("createdAt"), createdAfter);
    }

    private Specification<PostModel> withCreatedBefore(LocalDateTime createdBefore) {
        return (root, query, criteriaBuilder) -> createdBefore == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.lessThan(root.get("createdAt"), createdBefore);
    }

    private Specification<PostModel> withDeletedStatus(Boolean deletedStatus) {
        return (root, query, criteriaBuilder) -> deletedStatus == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("deleted"), deletedStatus);
    }
}
