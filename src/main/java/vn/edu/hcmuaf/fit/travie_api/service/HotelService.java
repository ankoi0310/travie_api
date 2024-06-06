package vn.edu.hcmuaf.fit.travie_api.service;

import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.*;

import java.util.List;

public interface HotelService {
    List<HotelDTO> search(HotelSearch hotelSearch);

    HotelDTO getHotelById(long id) throws BaseException;

    HotelDTO createHotel(HotelCreate hotelCreate) throws BaseException;

    HotelDTO updateHotel(long id, HotelCreate hotelCreate) throws BaseException;

    void updateHotelStatus(long id, boolean status) throws BaseException;
}
