package vn.edu.hcmuaf.fit.travie_api.service.hotel;

import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.*;

import java.util.List;

public interface HotelService {
    List<HotelDTO> search(HotelSearch hotelSearch);

    HotelDTO getHotelById(Long id) throws BaseException;

    HotelDTO createHotel(HotelCreate hotelCreate) throws BaseException;

    HotelDTO updateHotel(Long id, HotelCreate hotelCreate) throws BaseException;

    void updateHotelStatus(Long id, boolean status) throws BaseException;
}
