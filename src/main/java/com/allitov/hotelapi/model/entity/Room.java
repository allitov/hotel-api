package com.allitov.hotelapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private List<UnavailableDates> unavailableDates;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    private Hotel hotel;
}
