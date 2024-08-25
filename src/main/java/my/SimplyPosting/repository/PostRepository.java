package my.SimplyPosting.repository;

import my.SimplyPosting.model.PostModel;
import my.SimplyPosting.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostModel, Long> {

}
