package vn.edu.hcmuaf.fit.travie_api.repository.room;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Facility;
import vn.edu.hcmuaf.fit.travie_api.entity.Room;

import java.util.List;

@NoRepositoryBean
public interface RoomRepository extends CustomRepository<Room, Long> {
    List<Room> findAllByHotelId(Long hotelId);

    List<Room> findAllByFacilities(List<Facility> facilities);
}
