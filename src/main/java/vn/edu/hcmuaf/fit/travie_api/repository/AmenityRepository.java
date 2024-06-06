package vn.edu.hcmuaf.fit.travie_api.repository;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Amenity;

import java.util.Optional;

@NoRepositoryBean
public interface AmenityRepository extends CustomRepository<Amenity, Long> {
    Optional<Amenity> findByName(String name);
}
