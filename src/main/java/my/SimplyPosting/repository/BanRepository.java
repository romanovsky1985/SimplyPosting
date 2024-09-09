package my.SimplyPosting.repository;

import my.SimplyPosting.model.BanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BanRepository extends JpaRepository<BanModel, Long>, JpaSpecificationExecutor<BanModel> {
}
