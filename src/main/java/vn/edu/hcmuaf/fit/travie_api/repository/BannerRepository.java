package vn.edu.hcmuaf.fit.travie_api.repository;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Banner;

import java.util.Optional;

@NoRepositoryBean
public interface BannerRepository extends CustomRepository<Banner, Long> {
    Optional<Banner> findByTitle(String title);
}
