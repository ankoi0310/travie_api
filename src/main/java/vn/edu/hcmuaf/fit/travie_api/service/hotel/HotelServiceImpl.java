package vn.edu.hcmuaf.fit.travie_api.service.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.FacilityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelCreate;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.mapper.HotelMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.facility.FacilityRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.hotel.HotelRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.room.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final FacilityRepository facilityRepository;
    private final HotelMapper hotelMapper;

    @Override
    public List<HotelDTO> getHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotelMapper.toDTOs(hotels);
    }

    @Override
    public HotelDTO getHotelById(Long id) throws BaseException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new BaseException("Hotel not found"));
        return hotelMapper.toDTO(hotel);
    }

    @Override
    public HotelDTO createHotel(HotelCreate hotelCreate) throws BaseException {
        try {
            Optional<Hotel> hotelByName = hotelRepository.findByName(hotelCreate.getName());
            if (hotelByName.isPresent()) {
                throw new BadRequestException("Tên khách sạn đã tồn tại");
            }

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
                List<Long> facilityIds = roomCreate.getFacilities().stream().mapToLong(FacilityDTO::getId).boxed()
                                                   .toList();
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
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceBusinessException("Không thể tạo khách sạn");
        }
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelCreate hotelCreate) throws BaseException {
        return null;
    }

    @Override
    public void updateHotelStatus(Long id, boolean status) throws BaseException {

    }
}
