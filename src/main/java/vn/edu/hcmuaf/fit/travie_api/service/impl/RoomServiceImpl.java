package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.exception.*;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.AmenityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.room.*;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.mapper.RoomMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.*;
import vn.edu.hcmuaf.fit.travie_api.service.RoomService;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final AmenityRepository amenityRepository;

    private final RoomMapper mapper;

    @Override
    public List<RoomDTO> search(RoomSearch roomSearch) {
        List<Room> rooms = roomRepository.search(roomSearch);
        return mapper.toRoomDTOs(rooms);
    }

    @Override
    public List<RoomDTO> getRoomsByHotelId(long hotelId) throws BaseException {
        try {
            hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel not found"));

            List<Room> rooms = roomRepository.findAllByHotelId(hotelId);
            return mapper.toRoomDTOs(rooms);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceBusinessException("Error when get rooms by hotel id");
        }
    }

    @Override
    public RoomDTO getRoomById(long id) throws BaseException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room not found"));
        return mapper.toRoomDTO(room);
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

            List<Long> amenityIds = roomCreate.getAmenities().stream().map(AmenityDTO::getId).toList();
            List<Amenity> amenities = amenityRepository.findAllById(amenityIds);

            Room newRoom = Room.builder()
                               .name(roomCreate.getName())
                               .description(roomCreate.getDescription())
                               .price(roomCreate.getPrice())
                               .hotel(hotelById)
                               .amenities(amenities)
                               .build();

            Room room = roomRepository.save(newRoom);
            return mapper.toRoomDTO(room);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error when create room", e);
            throw new ServiceBusinessException("Error when create room");
        }
    }

    @Override
    public RoomDTO updateRoom(long id, RoomUpdate roomUpdate) throws BaseException {
        return null;
    }

    @Override
    public void updateRoomStatus(long id, boolean status) throws BaseException {

    }

    @Override
    public void deleteRoom(long id) throws BaseException {
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
