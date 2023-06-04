package com.dydek.mjm.FollowedShips.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShipCoordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Date date;

    private Double positionLong;

    private Double positionLat;

    @ManyToOne(targetEntity = Ship.class)
    @JoinColumn(name = "ship")
    private Ship ship;


}
