package com.dydek.mjm.FollowedShips.Service;

import com.dydek.mjm.FollowedShips.DTO.ShipDTO;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ShipService {
    List<ShipDTO> getUsersShips();

    ShipDTO getShip(Long id);

    void addShipToTrackingSystem(Integer mmsi) throws NameNotFoundException;

    void removeShipFromTrackingSystem(Long shipId);

}
