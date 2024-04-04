package vn.edu.hcmuaf.fit.travie_api.repository.room;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.*;

import java.util.List;

@Repository
public class RoomRepositoryImpl extends AbstractRepository<Room, Long> implements RoomRepository {
    private final QRoom qRoom = QRoom.room;

    protected RoomRepositoryImpl(EntityManager entityManager) {
        super(Room.class, entityManager);
    }

    @Override
    public List<Room> findAllByHotelId(Long hotelId) {
        return null;
    }

    @Override
    public List<Room> findAllByFacilities(List<Facility> facilities) {
        return null;
    }
}
