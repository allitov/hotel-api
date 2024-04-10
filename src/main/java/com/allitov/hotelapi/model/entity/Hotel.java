package com.allitov.hotelapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * The class that represents an entity of a hotel.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @EqualsAndHashCode.Exclude
    private String description;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "distance_from_center")
    @EqualsAndHashCode.Exclude
    private Float distanceFromCenter;

    @Column(name = "rating")
    @EqualsAndHashCode.Exclude
    private Float rating;

    @Column(name = "number_of_ratings")
    @EqualsAndHashCode.Exclude
    private Integer numberOfRatings;
}
