package my.SimplyPosting.repository;

import my.SimplyPosting.model.UserModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    List<UserModel> findAllByDeleted(boolean deleted, PageRequest pageRequest);
}
