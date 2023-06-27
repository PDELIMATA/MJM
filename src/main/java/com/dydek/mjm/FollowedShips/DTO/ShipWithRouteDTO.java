package com.dydek.mjm.FollowedShips.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShipWithRouteDTO {
    ShipWithCoordinatesDTO ship;
    List<ShipCoordinatesDTO> route;
}
