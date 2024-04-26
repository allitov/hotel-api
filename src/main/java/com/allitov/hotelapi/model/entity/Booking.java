package com.allitov.hotelapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * The class that represents an entity of a booking.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "from_date")
    private LocalDate from;

    @Column(name = "to_date")
    private LocalDate to;
}
