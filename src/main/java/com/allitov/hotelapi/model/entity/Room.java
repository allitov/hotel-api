package com.allitov.hotelapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * The class that represents an entity of a hotel room.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "number")
    private Short number;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "max_people")
    private Short maxPeople;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "unavailable_dates",
            joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id")
    )
    private List<LocalDate> unavailableDates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;
}
