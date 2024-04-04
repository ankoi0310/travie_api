package vn.edu.hcmuaf.fit.travie_api.repository.hotel;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Hotel;

import java.util.Optional;

@NoRepositoryBean
public interface HotelRepository extends CustomRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);
}
