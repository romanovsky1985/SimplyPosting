package my.SimplyPosting.specification;

import my.SimplyPosting.dto.ban.BanFilterDTO;
import my.SimplyPosting.model.BanModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BanSpecification {

    public Specification<BanModel> build(BanFilterDTO filterDTO) {
        return withModeratorId(filterDTO.getModeratorId())
                .and(withOffenderId(filterDTO.getOffenderId()))
                .and(withInfoContain(filterDTO.getInfoContain()))
                .and(withCreatedAfter(filterDTO.getCreatedAfter()))
                .and(withCreatedBefore(filterDTO.getCreatedBefore()));
    }

    private Specification<BanModel> withModeratorId(Long moderatorId) {
        return (root, query, criteriaBuilder) -> moderatorId == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("moderator").get("id"), moderatorId);
    }

    private Specification<BanModel> withOffenderId(Long offenderId) {
        return (root, query, criteriaBuilder) -> offenderId == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("offender").get("id"), offenderId);
    }

    private Specification<BanModel> withInfoContain(String infoContain) {
        return (root, query, criteriaBuilder) -> infoContain == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.like(root.get("info"), "%" + infoContain + "%");
    }

    private Specification<BanModel> withCreatedAfter(LocalDateTime createdAfter) {
        return (root, query, criteriaBuilder) -> createdAfter == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.greaterThan(root.get("createdAt"), createdAfter);
    }

    private Specification<BanModel> withCreatedBefore(LocalDateTime createdBefore) {
        return (root, query, criteriaBuilder) -> createdBefore == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.lessThan(root.get("createdAt"), createdBefore);
    }

}
