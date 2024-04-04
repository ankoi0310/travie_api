package vn.edu.hcmuaf.fit.travie_api.service.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.FacilityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelCreate;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.mapper.FacilityMapper;
import vn.edu.hcmuaf.fit.travie_api.mapper.HotelMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.facility.FacilityRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.hotel.HotelRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.room.RoomRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final FacilityRepository facilityRepository;
    private final HotelMapper hotelMapper;
    private final FacilityMapper facilityMapper;

    @Override
    public HotelDTO createHotel(HotelCreate hotelCreate) {
        // Find a hotel by name
        hotelRepository.findByName(hotelCreate.getName()).ifPresent(hotel -> {
            throw new IllegalArgumentException("Hotel name already exists");
        });

        // Create new address
        Address newAddress = Address.builder()
                                    .detail(hotelCreate.getAddress().getDetail())
                                    .wardId(hotelCreate.getAddress().getWardId())
                                    .districtId(hotelCreate.getAddress().getDistrictId())
                                    .provinceId(hotelCreate.getAddress().getProvinceId())
                                    .fullAddress(hotelCreate.getAddress().getFullAddress())
                                    .build();

        Hotel newHotel = Hotel.builder()
                              .name(hotelCreate.getName())
                              .description(hotelCreate.getDescription())
                              .address(newAddress)
                              .build();

        // Get all facility ids and check if they exist
//        List<Long> facilityIds = hotelCreate.getAllFacilityIds();
//        List<Facility> facilities = facilityRepository.findAllById(facilityIds);
//
//        if (facilities.size() != facilityIds.size()) {
//            throw new IllegalArgumentException("Facility not found");
//        }

        // Add rooms to newHotel
        hotelCreate.getRooms().forEach(roomCreate -> {
            List<Long> facilityIds = roomCreate.getFacilities().stream().mapToLong(FacilityDTO::getId).boxed().toList();
            List<Facility> facilities = facilityRepository.findAllById(facilityIds);

            Room newRoom = Room.builder()
                               .name(roomCreate.getName())
                               .description(roomCreate.getDescription())
                               .price(roomCreate.getPrice())
                               .facilities(facilities)
                               .build();

            roomRepository.save(newRoom);

            newHotel.addRoom(newRoom);
        });

        return hotelMapper.toDTO(hotelRepository.save(newHotel));
    }
}
