package my.SimplyPosting.repository;

import my.SimplyPosting.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostModel, Long> {

}
