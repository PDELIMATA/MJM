package com.dydek.mjm.FollowedShips.Service;

import com.dydek.mjm.FollowedShips.DTO.ShipCoordinatesDTO;

import java.util.List;

public interface ShipCoordinatesService {
    List<ShipCoordinatesDTO> getShipsCoordinates(Long id);
    void updateShipLocations();
}
