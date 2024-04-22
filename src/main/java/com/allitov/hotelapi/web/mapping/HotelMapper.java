package com.allitov.hotelapi.web.mapping;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.web.dto.request.HotelRequest;
import com.allitov.hotelapi.web.dto.response.HotelListResponse;
import com.allitov.hotelapi.web.dto.response.HotelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * The mapping interface for the hotel entity.
 * @author allitov
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    /**
     * Creates a hotel entity from a hotel request DTO and returns it.
     * @param request a hotel request DTO to create a hotel entity from.
     * @return a hotel entity.
     */
    Hotel requestToEntity(HotelRequest request);

    /**
     * Creates a hotel response DTO from a hotel entity and returns it.
     * @param hotel a hotel entity to create a hotel response DTO from.
     * @return a hotel response DTO.
     */
    HotelResponse entityToResponse(Hotel hotel);

    /**
     * Creates a hotel list response DTO from a list of hotel entities and returns it.
     * @param hotels a list of hotel entities to create a hotel list response DTO from.
     * @return a hotel list response DTO.
     */
    default HotelListResponse entityListToListResponse(List<Hotel> hotels) {
        HotelListResponse response = new HotelListResponse();
        response.setHotels(hotels.stream().map(this::entityToResponse).toList());

        return response;
    }
}
