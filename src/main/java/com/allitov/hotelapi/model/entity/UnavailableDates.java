package com.allitov.hotelapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * The class that represents an entity of a hotel room unavailable dates.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unavailable_dates")
public class UnavailableDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @Column(name = "from_date")
    private LocalDate from;

    @Column(name = "to_date")
    private LocalDate to;
}
