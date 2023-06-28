package com.dydek.mjm.FollowedShips.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipCoordinatesDTO {

    private Date date;
    private Double positionLong;
    private Double positionLat;
}
