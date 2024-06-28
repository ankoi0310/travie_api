package vn.edu.hcmuaf.fit.travie_api.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomRepository<T, Long> extends JpaRepository<T, Long> {
}
