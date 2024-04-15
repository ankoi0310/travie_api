package vn.edu.hcmuaf.fit.travie_api.service.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.FacilityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.room.*;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.mapper.RoomMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.facility.FacilityRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.hotel.HotelRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.room.RoomRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final FacilityRepository facilityRepository;

    private final RoomMapper roomMapper;

    @Override
    public List<RoomDTO> getRooms() {
        List<Room> rooms = roomRepository.findAll();
        return roomMapper.toDTOs(rooms);
    }

    @Override
    public List<RoomDTO> getRoomsByHotelId(Long hotelId) throws BaseException {
        try {
            hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel not found"));

            List<Room> rooms = roomRepository.findAllByHotelId(hotelId);
            return roomMapper.toDTOs(rooms);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceBusinessException("Error when get rooms by hotel id");
        }
    }

    @Override
    public RoomDTO getRoomById(Long id) throws BaseException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room not found"));
        return roomMapper.toDTO(room);
    }

    @Override
    public RoomDTO createRoom(RoomCreate roomCreate) throws BaseException {
        try {
            Optional<Room> roomByName = roomRepository.findByName(roomCreate.getName());
            if (roomByName.isPresent()) {
                throw new BadRequestException("Room name already exists");
            }

            Hotel hotelById = hotelRepository.findById(roomCreate.getHotel().getId())
                                             .orElseThrow(() -> new NotFoundException("Hotel not found"));

            List<Long> facilityIds = roomCreate.getFacilities().stream().map(FacilityDTO::getId).toList();
            List<Facility> facilities = facilityRepository.findAllById(facilityIds);

            Room newRoom = Room.builder()
                               .name(roomCreate.getName())
                               .description(roomCreate.getDescription())
                               .price(roomCreate.getPrice())
                               .hotel(hotelById)
                               .facilities(facilities)
                               .build();
            
            return roomMapper.toDTO(roomRepository.save(newRoom));
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error when create room", e);
            throw new ServiceBusinessException("Error when create room");
        }
    }

    @Override
    public RoomDTO updateRoom(Long id, RoomUpdate roomUpdate) throws BaseException {
        return null;
    }

    @Override
    public void updateRoomStatus(Long id, boolean status) throws BaseException {

    }

    @Override
    public void deleteRoom(Long id) throws BaseException {
        try {
            Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room not found"));

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error when delete room", e);
            throw new ServiceBusinessException("Error when delete room");
        }
    }
}
