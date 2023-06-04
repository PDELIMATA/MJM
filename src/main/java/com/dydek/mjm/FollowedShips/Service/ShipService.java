package com.dydek.mjm.FollowedShips.Service;

import com.dydek.mjm.FollowedShips.DTO.ShipCoordinatesDTO;
import com.dydek.mjm.FollowedShips.DTO.ShipDTO;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ShipService {
    List<ShipDTO> getUsersShips(String username);

    List<ShipCoordinatesDTO> getShipsCoordinates(Long id);

    ShipDTO getShip(Long id);

    void addShipToTrackingSystem(String username, Integer mmsi) throws NameNotFoundException;

    void removeShipFromTrackingSystem(String username, Long shipPublicId);

}
