package com.dydek.mjm.FollowedShips.Service;

import com.dydek.mjm.FollowedShips.DTO.ShipDTO;
import com.dydek.mjm.FollowedShips.DTO.ShipWithRouteDTO;
import com.dydek.mjm.FollowedShips.Entity.Ship;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ShipService {
    List<Ship> getUsersShips(String username);

    List<ShipWithRouteDTO> getUserShipsWithRoutes(String username);

    List<ShipDTO> getMmsiShipAddedToTS(String username);

    ShipDTO getShip(Long id);

    void addShipToTrackingSystem(String username, Integer mmsi) throws NameNotFoundException;

    void removeShipFromTrackingSystem(String username, Long shipId);

}