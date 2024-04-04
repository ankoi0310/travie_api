package vn.edu.hcmuaf.fit.travie_api.dto.facility;

import lombok.Data;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;

import java.util.List;

@Data
public class FacilityUpdate {
    private Long id;
    private String name;
    private List<RoomDTO> rooms;
}
