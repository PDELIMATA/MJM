package com.dydek.mjm.FollowedShips.Entity;

import com.dydek.mjm.User.Entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ship")
@Getter
@Setter
@NoArgsConstructor
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer mmsi;

    private Integer shipType;

    private String name;

    @OneToMany(mappedBy = "ship", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = ShipCoordinates.class)
    private List<Ship> coordinates;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    public Ship(Integer mmsi, Integer shipType, String name, User user) {
        this.mmsi = mmsi;
        this.shipType = shipType;
        this.name = name;
        this.user = user;
    }
}
