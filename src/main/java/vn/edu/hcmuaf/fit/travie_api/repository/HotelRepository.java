package vn.edu.hcmuaf.fit.travie_api.repository;

import com.querydsl.core.BooleanBuilder;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelSearch;
import vn.edu.hcmuaf.fit.travie_api.entity.Hotel;

import java.util.Optional;

@NoRepositoryBean
public interface HotelRepository extends CustomRepository<Hotel, Long> {
    @NonNull
    Page<Hotel> findAll(BooleanBuilder predicate, @NonNull Pageable pageable);

    Page<Hotel> search(HotelSearch hotelSearch, Pageable pageable);

    Page<Hotel> findNearbyHotels(String location, Pageable pageable);

    Page<Hotel> findByOrderByAverageMarkDesc(Pageable pageable);

    Page<Hotel> findByOrderByCreatedDateDesc(Pageable pageable);

    Optional<Hotel> findByName(String name);
}
