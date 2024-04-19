package com.allitov.hotelapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * The class that represents an entity of a user.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @EqualsAndHashCode.Exclude
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    private RoleType role;

    /**
     * The enumeration that represents available user roles.
     * @author allitov
     */
    public enum RoleType {
        USER, ADMIN
    }
}
