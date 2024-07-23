package com.allitov.hotelapi.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

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
@FieldNameConstants
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Integer id;

    @Column(name = "description")
    @EqualsAndHashCode.Exclude
    private String description;

    @Column(name = "number")
    private Short number;

    @Column(name = "price")
    @EqualsAndHashCode.Exclude
    private BigDecimal price;

    @Column(name = "max_people")
    @EqualsAndHashCode.Exclude
    private Short maxPeople;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private List<UnavailableDates> unavailableDates;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;
}
