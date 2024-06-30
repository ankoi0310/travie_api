package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
    public Page<HotelDTO> getAllHotels(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        return hotels.map(hotelMapper::toDTO);
    }

    @Override
    public Page<HotelDTO> search(HotelSearch hotelSearch) {
        Pageable pageable = PageRequest.of(hotelSearch.getPage() - 1, hotelSearch.getSize());
        Page<Hotel> hotels = hotelRepository.search(hotelSearch, pageable);
        return hotels.map(hotelMapper::toDTO);
    }

    @Override
    public Page<HotelDTO> getNearbyHotels(String location, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Hotel> hotels = hotelRepository.findNearbyHotels(location, pageable);
        return hotels.map(hotelMapper::toDTO);
    }

    @Override
    public Page<HotelDTO> getPopularHotels(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Hotel> hotels = hotelRepository.findByOrderByAverageMarkDesc(pageable);
        return hotels.map(hotelMapper::toDTO);
    }

    @Override
    public Page<HotelDTO> getExploreHotels(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Hotel> hotels = hotelRepository.findByOrderByCreatedDateDesc(pageable);
        return hotels.map(hotelMapper::toDTO);
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
