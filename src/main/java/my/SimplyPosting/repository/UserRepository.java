package my.SimplyPosting.repository;

import my.SimplyPosting.model.UserModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long>, JpaSpecificationExecutor<UserModel> {
    Optional<UserModel> findByUsername(String username);
    Boolean existsByUsername(String username);
}
