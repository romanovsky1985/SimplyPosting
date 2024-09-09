package my.SimplyPosting.specification;

import my.SimplyPosting.dto.user.UserFilterDTO;
import my.SimplyPosting.model.UserModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserSpecification {

    public Specification<UserModel> build(UserFilterDTO filterDTO) {
        return withUsernameContain(filterDTO.getUsernameContains())
                .and(withCreatedAfter(filterDTO.getCreatedAfter()))
                .and(withCreatedBefore(filterDTO.getCreatedBefore()))
                .and(withDeletedStatus(filterDTO.getDeletedStatus()))
                .and(withBannedStatus(filterDTO.getBannedStatus()));
    }

    private Specification<UserModel> withUsernameContain(String usernameContain) {
        return (root, query, criteriaBuilder) -> usernameContain == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.like(root.get("username"), "%" + usernameContain + "%");

    }

    private Specification<UserModel> withCreatedAfter(LocalDateTime createdAfter) {
        return (root, query, criteriaBuilder) -> createdAfter == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.greaterThan(root.get("createdAt"), createdAfter);
    }

    private Specification<UserModel> withCreatedBefore(LocalDateTime createdBefore) {
        return (root, query, criteriaBuilder) -> createdBefore == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.lessThan(root.get("createdAt"), createdBefore);
    }

    private Specification<UserModel> withDeletedStatus(Boolean deletedStatus) {
        return (root, query, criteriaBuilder) -> deletedStatus == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("deleted"), deletedStatus);
    }

    private Specification<UserModel> withBannedStatus(Boolean bannedStatus) {
        return (root, query, criteriaBuilder) -> bannedStatus == null ? criteriaBuilder.conjunction() :
                bannedStatus ? criteriaBuilder.greaterThan(root.get("bannedBefore"), LocalDateTime.now()) :
                        criteriaBuilder.lessThan(root.get("bannedBefore"), LocalDateTime.now());

    }

}
