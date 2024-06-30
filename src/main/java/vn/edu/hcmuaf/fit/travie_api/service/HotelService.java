package vn.edu.hcmuaf.fit.travie_api.service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.*;

public interface HotelService {
    Page<HotelDTO> getAllHotels(int page, int size);

    Page<HotelDTO> search(HotelSearch hotelSearch);

    Page<HotelDTO> getNearbyHotels(String location, int page, int size);

    Page<HotelDTO> getPopularHotels(int page, int size);

    Page<HotelDTO> getExploreHotels(int page, int size);

    HotelDTO getHotelById(long id) throws BaseException;

    HotelDTO createHotel(HotelCreate hotelCreate) throws BaseException;

    HotelDTO updateHotel(long id, HotelCreate hotelCreate) throws BaseException;

    void updateHotelStatus(long id, boolean status) throws BaseException;
}
