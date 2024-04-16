package vn.edu.hcmuaf.fit.travie_api.service.room;

import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.room.*;

import java.util.List;

public interface RoomService {
    List<RoomDTO> search(RoomSearch roomSearch);

    List<RoomDTO> getRoomsByHotelId(Long hotelId) throws BaseException;

    RoomDTO getRoomById(Long id) throws BaseException;

    RoomDTO createRoom(RoomCreate roomCreate) throws BaseException;

    RoomDTO updateRoom(Long id, RoomUpdate roomUpdate) throws BaseException;

    void updateRoomStatus(Long id, boolean status) throws BaseException;

    void deleteRoom(Long id) throws BaseException;
}
