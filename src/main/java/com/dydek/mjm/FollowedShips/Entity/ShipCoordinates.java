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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;

    private Double positionLong;

    private Double positionLat;

    @ManyToOne(targetEntity = Ship.class)
    @JoinColumn(name = "ship_id")
    private Ship ship;


    public ShipCoordinates(Date date, Double positionLong, Double positionLat, Ship ship) {
        this.date = date;
        this.positionLong = positionLong;
        this.positionLat = positionLat;
        this.ship = ship;
    }
}
