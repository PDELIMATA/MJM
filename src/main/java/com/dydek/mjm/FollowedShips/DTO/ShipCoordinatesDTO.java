package com.dydek.mjm.FollowedShips.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ShipCoordinatesDTO {

    private Date date;
    private Double positionLong;
    private Double positionLat;
}
