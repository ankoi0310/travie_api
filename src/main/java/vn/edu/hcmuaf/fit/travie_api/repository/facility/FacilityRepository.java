package vn.edu.hcmuaf.fit.travie_api.repository.facility;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Facility;
import vn.edu.hcmuaf.fit.travie_api.entity.Room;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface FacilityRepository extends CustomRepository<Facility, Long> {
    List<Facility> findAllByRooms(List<Room> rooms);

    Optional<Facility> findByName(String name);
}
