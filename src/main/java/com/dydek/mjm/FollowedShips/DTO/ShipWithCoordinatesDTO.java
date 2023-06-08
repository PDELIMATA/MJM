package com.dydek.mjm.FollowedShips.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShipWithCoordinatesDTO {

    private ShipDTO shipDTO;
    private ShipCoordinatesDTO shipCoordinatesDTO;

}
