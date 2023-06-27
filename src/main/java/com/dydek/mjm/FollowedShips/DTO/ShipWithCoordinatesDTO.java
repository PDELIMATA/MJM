package com.dydek.mjm.FollowedShips.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipWithCoordinatesDTO {

    private ShipDTO shipDTO;
    private ShipCoordinatesDTO shipCoordinatesDTO;

}
