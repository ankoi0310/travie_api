package vn.edu.hcmuaf.fit.travie_api.service.hotel;

import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelCreate;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelDTO;

import java.util.List;

public interface HotelService {
    List<HotelDTO> getHotels();

    HotelDTO getHotelById(Long id) throws BaseException;

    HotelDTO createHotel(HotelCreate hotelCreate) throws BaseException;

    HotelDTO updateHotel(Long id, HotelCreate hotelCreate) throws BaseException;

    void updateHotelStatus(Long id, boolean status) throws BaseException;
}
