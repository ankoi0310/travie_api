package vn.edu.hcmuaf.fit.travie_api.repository;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomSearch;
import vn.edu.hcmuaf.fit.travie_api.entity.Amenity;
import vn.edu.hcmuaf.fit.travie_api.entity.Room;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface RoomRepository extends CustomRepository<Room, Long> {
    List<Room> search(RoomSearch roomSearch);

    List<Room> findAllByHotelId(long hotelId);

    List<Room> findAllByAmenities(List<Amenity> amenities);

    Optional<Room> findByName(String name);
}
