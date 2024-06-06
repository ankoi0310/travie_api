package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.exception.*;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.*;
import vn.edu.hcmuaf.fit.travie_api.entity.Amenity;
import vn.edu.hcmuaf.fit.travie_api.mapper.AmenityMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.AmenityRepository;
import vn.edu.hcmuaf.fit.travie_api.service.AmenityService;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {
    private final AmenityRepository amenityRepository;
    private final AmenityMapper mapper;

    @Override
    public List<AmenityDTO> getAllAmenities() {
        List<Amenity> amenities = amenityRepository.findAll();
        return mapper.toAmenityDTOs(amenities);
    }

    @Override
    public AmenityDTO getAmenityById(long id) throws BaseException {
        return amenityRepository.findById(id)
                                .map(mapper::toAmenityDTO)
                                .orElseThrow(() -> new NotFoundException("Không tìm thấy tiện ích"));
    }

    @Override
    public AmenityDTO createAmenity(AmenityCreate amenityCreate) throws BaseException {
        amenityRepository.findByName(amenityCreate.getName())
                         .orElseThrow(() -> new BadRequestException("Tiện ích đã tồn tại"));

        Amenity newAmenity = Amenity.builder()
                                    .name(amenityCreate.getName())
                                    .build();

        amenityRepository.save(newAmenity);

        return mapper.toAmenityDTO(newAmenity);
    }

    @Override
    public AmenityDTO updateAmenity(AmenityUpdate amenityUpdate) throws BaseException {
        Amenity amenity = amenityRepository.findById(amenityUpdate.getId())
                                           .orElseThrow(() -> new NotFoundException("Không tìm thấy tiện ích"));

        Optional<Amenity> amenityByName = amenityRepository.findByName(amenityUpdate.getName());
        if (amenityByName.isPresent() && amenityByName.get().getId() != amenityUpdate.getId()) {
            throw new BadRequestException("Tiện ích đã tồn tại");
        }

        amenity.setName(amenityUpdate.getName());
        amenityRepository.save(amenity);

        return mapper.toAmenityDTO(amenity);
    }

    @Override
    public void updateAmenityStatus(long id, boolean delete) throws BaseException {
        Amenity amenity = amenityRepository.findById(id)
                                           .orElseThrow(() -> new NotFoundException("Không tìm thấy tiện ích"));

        amenity.setDeleted(delete);
        amenityRepository.save(amenity);
    }
}
