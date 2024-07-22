package com.allitov.hotelapi.model.repository.specification;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.web.dto.filter.HotelFilter;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * The specification class for the hotel repository.
 * @author allitov
 */
@UtilityClass
public class HotelSpecification {

    /**
     * Returns specification for filtering hotel entities.
     * @param filter a filter by which the specification is built.
     * @return a specification for filtering hotel entities.
     */
    public Specification<Hotel> withFilter(HotelFilter filter) {
        return Specification
                .where(byIdsIn(filter.getId()))
                .and(byName(filter.getName()))
                .and(byCity(filter.getCity()))
                .and(byAddress(filter.getAddress()))
                .and(byDistanceFromCenter(filter.getDistanceFromCenter()))
                .and(byRating(filter.getRating()))
                .and(byNumberOfRatings(filter.getNumberOfRatings()));
    }

    private Specification<Hotel> byIdsIn(List<Integer> ids) {
        return (root, query, builder) -> {
            if (ids == null) {
                return null;
            }

            return builder.in(root.get(Hotel.Fields.id)).value(ids);
        };
    }

    private Specification<Hotel> byName(String name) {
        return (root, query, builder) -> {
            if (name == null) {
                return null;
            }

            return builder.equal(root.get(Hotel.Fields.name), name);
        };
    }

    private Specification<Hotel> byCity(String city) {
        return (root, query, builder) -> {
            if (city == null) {
                return null;
            }

            return builder.equal(root.get(Hotel.Fields.city), city);
        };
    }

    private Specification<Hotel> byAddress(String address) {
        return (root, query, builder) -> {
            if (address == null) {
                return null;
            }

            return builder.equal(root.get(Hotel.Fields.address), address);
        };
    }

    private Specification<Hotel> byDistanceFromCenter(Float distanceFromCenter) {
        return (root, query, builder) -> {
            if (distanceFromCenter == null) {
                return null;
            }

            return builder.lessThanOrEqualTo(root.get(Hotel.Fields.distanceFromCenter), distanceFromCenter);
        };
    }

    private Specification<Hotel> byRating(Float rating) {
        return (root, query, builder) -> {
            if (rating == null) {
                return null;
            }

            return builder.greaterThanOrEqualTo(root.get(Hotel.Fields.rating), rating);
        };
    }

    private Specification<Hotel> byNumberOfRatings(Integer numberOfRatings) {
        return (root, query, builder) -> {
            if (numberOfRatings == null) {
                return null;
            }

            return builder.greaterThanOrEqualTo(root.get(Hotel.Fields.numberOfRatings), numberOfRatings);
        };
    }
}
