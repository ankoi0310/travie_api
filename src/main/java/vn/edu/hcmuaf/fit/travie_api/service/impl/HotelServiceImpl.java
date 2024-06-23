package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BadRequestException;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.AmenityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.*;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.mapper.HotelMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.*;
import vn.edu.hcmuaf.fit.travie_api.service.HotelService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final AmenityRepository amenityRepository;
    private final HotelMapper hotelMapper;

    @Override
    public List<HotelDTO> search(HotelSearch hotelSearch) {
        List<Hotel> hotels = hotelRepository.search(hotelSearch);
        return hotelMapper.toDTOs(hotels);
    }

    @Override
    public List<HotelDTO> getNearbyHotels(String location) {
        List<Hotel> hotels = hotelRepository.findNearbyHotels(location);
        return hotelMapper.toDTOs(hotels);
    }

    @Override
    public List<HotelDTO> getPopularHotels() {
        List<Hotel> hotels = hotelRepository.findTop5ByOrderByAverageMarkDesc();
        return hotelMapper.toDTOs(hotels);
    }

    @Override
    public HotelDTO getHotelById(long id) throws BaseException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new BaseException("Hotel not found"));
        return hotelMapper.toDTO(hotel);
    }

    @Override
    public HotelDTO createHotel(HotelCreate hotelCreate) throws BaseException {
        Optional<Hotel> hotelByName = hotelRepository.findByName(hotelCreate.getName());
        if (hotelByName.isPresent()) {
            throw new BadRequestException("Tên khách sạn đã tồn tại");
        }

        // Create new address
        Address newAddress = Address.builder().detail(hotelCreate.getAddress().getDetail())
                                    .wardId(hotelCreate.getAddress().getWardId())
                                    .districtId(hotelCreate.getAddress().getDistrictId())
                                    .provinceId(hotelCreate.getAddress().getProvinceId())
                                    .fullAddress(hotelCreate.getAddress().getFullAddress()).build();

        Hotel newHotel = Hotel.builder().name(hotelCreate.getName()).introduction(hotelCreate.getIntroduction())
                              .address(newAddress).build();

        // Get all amenity ids and check if they exist
//        List<long> amenityIds = hotelCreate.getAllAmenityIds();
//        List<Amenity> amenities = amenityRepository.findAllById(amenityIds);
//
//        if (amenities.size() != amenityIds.size()) {
//            throw new IllegalArgumentException("Amenity not found");
//        }

        // Add rooms to newHotel
        hotelCreate.getRooms().forEach(roomCreate -> {
            List<Long> amenityIds = roomCreate.getAmenities().stream().mapToLong(AmenityDTO::getId).boxed().toList();
            List<Amenity> amenities = amenityRepository.findAllById(amenityIds);

            Room newRoom = Room.builder().name(roomCreate.getName())
                               .firstHoursOrigin(roomCreate.getFirstHoursOrigin()).amenities(amenities).build();

            roomRepository.save(newRoom);

            newHotel.addRoom(newRoom);
        });

        return hotelMapper.toDTO(hotelRepository.save(newHotel));
    }

    @Override
    public HotelDTO updateHotel(long id, HotelCreate hotelCreate) throws BaseException {
        return null;
    }

    @Override
    public void updateHotelStatus(long id, boolean status) throws BaseException {

    }
}
