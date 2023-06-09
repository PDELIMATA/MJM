package com.dydek.mjm.FollowedShips.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShipWithRouteDTO {
    ShipDTO ship;
    List<ShipCoordinatesDTO> route;
}
