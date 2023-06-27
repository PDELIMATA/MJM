package com.dydek.mjm.FollowedShips.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ShipCoordinatesDTO {

    private Date date;
    private Double positionLong;
    private Double positionLat;
}
