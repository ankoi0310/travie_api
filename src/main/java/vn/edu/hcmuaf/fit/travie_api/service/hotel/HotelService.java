package vn.edu.hcmuaf.fit.travie_api.service.hotel;

import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelCreate;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelDTO;

public interface HotelService {
    HotelDTO createHotel(HotelCreate hotelCreate);
}
