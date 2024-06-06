package vn.edu.hcmuaf.fit.travie_api.service;

import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.room.*;

import java.util.List;

public interface RoomService {
    List<RoomDTO> search(RoomSearch roomSearch);

    List<RoomDTO> getRoomsByHotelId(long hotelId) throws BaseException;

    RoomDTO getRoomById(long id) throws BaseException;

    RoomDTO createRoom(RoomCreate roomCreate) throws BaseException;

    RoomDTO updateRoom(long id, RoomUpdate roomUpdate) throws BaseException;

    void updateRoomStatus(long id, boolean status) throws BaseException;

    void deleteRoom(long id) throws BaseException;
}
