package com.allitov.hotelapi.model.repository.specification;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.model.entity.UnavailableDates;
import com.allitov.hotelapi.web.dto.filter.RoomFilter;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

/**
 * The specification class for the room repository.
 * @author allitov
 */
@UtilityClass
public class RoomSpecification {

    /**
     * Returns specification for filtering room entities.
     * @param filter a filter by which the specification is built.
     * @return a specification for filtering room entities.
     */
    public Specification<Room> withFilter(RoomFilter filter) {
        return Specification
                .where(byIdsIn(filter.getId()))
                .and(byDescription(filter.getDescription()))
                .and(byPrice(filter.getMinPrice(), filter.getMaxPrice()))
                .and(byMaxPeople(filter.getMaxPeople()))
                .and(byDate(filter.getFrom(), filter.getTo()))
                .and(byHotelId(filter.getHotelId()));
    }

    private Specification<Room> byIdsIn(Collection<Integer> ids) {
        return (root, query, builder) -> {
            if (ids == null) {
                return null;
            }

            return builder.in(root.get(Room.Fields.id)).value(ids);
        };
    }

    private Specification<Room> byDescription(String description) {
        return (root, query, builder) -> {
            if (description == null) {
                return null;
            }

            return builder.equal(root.get(Room.Fields.description), description);
        };
    }

    private Specification<Room> byPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, builder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }

            if (minPrice == null) {
                return builder.lessThanOrEqualTo(root.get(Room.Fields.price), maxPrice);
            } else if (maxPrice == null) {
                return builder.greaterThanOrEqualTo(root.get(Room.Fields.price), minPrice);
            } else {
                return builder.between(root.get(Room.Fields.price), minPrice, maxPrice);
            }
        };
    }

    private Specification<Room> byMaxPeople(Short maxPeople) {
        return (root, query, builder) -> {
            if (maxPeople == null) {
                return null;
            }

            return builder.lessThanOrEqualTo(root.get(Room.Fields.maxPeople), maxPeople);
        };
    }

    private Specification<Room> byDate(LocalDate from, LocalDate to) {
        return (root, query, builder) -> {
            if (from == null || to == null) {
                return null;
            }

            var start = builder.lessThanOrEqualTo(
                    root.get(Room.Fields.unavailableDates).get(UnavailableDates.Fields.from), to);
            var end = builder.greaterThanOrEqualTo(
                    root.get(Room.Fields.unavailableDates).get(UnavailableDates.Fields.to), from);
            var overlaps = builder.and(start, end);

            var subquery = query.subquery(Integer.class);
            var join = subquery.from(UnavailableDates.class).join(UnavailableDates.Fields.room);
            subquery.select(join.get(Room.Fields.id)).where(overlaps).distinct(true);

            return builder.not(root.get(Room.Fields.id).in(subquery.getSelection()));
        };
    }

    private Specification<Room> byHotelId(Integer hotelId) {
        return (root, query, builder) -> {
            if (hotelId == null) {
                return null;
            }

            return builder.equal(root.get(Room.Fields.hotel).get(Hotel.Fields.id), hotelId);
        };
    }
}
