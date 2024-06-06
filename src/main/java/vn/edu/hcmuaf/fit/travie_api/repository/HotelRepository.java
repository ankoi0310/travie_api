package vn.edu.hcmuaf.fit.travie_api.repository;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelSearch;
import vn.edu.hcmuaf.fit.travie_api.entity.Hotel;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface HotelRepository extends CustomRepository<Hotel, Long> {
    List<Hotel> search(HotelSearch hotelSearch);

    Optional<Hotel> findByName(String name);
}
