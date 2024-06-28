package vn.edu.hcmuaf.fit.travie_api.dto.amenity;

import lombok.Data;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;

import java.util.List;

@Data
public class AmenityUpdate {
    private long id;
    private String name;
    private List<RoomDTO> rooms;
}
