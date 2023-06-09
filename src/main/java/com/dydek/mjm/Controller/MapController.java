package com.dydek.mjm.Controller;

import com.dydek.mjm.FollowedShips.DTO.ShipDTO;
import com.dydek.mjm.FollowedShips.DTO.ShipWithRouteDTO;
import com.dydek.mjm.FollowedShips.Service.ShipCoordinatesService;
import com.dydek.mjm.FollowedShips.Service.ShipService;
import com.dydek.mjm.Model.Ship;
import com.dydek.mjm.Service.TrackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/ships")
public class MapController {

    private final TrackService trackService;
    private final ShipService shipService;
    private final ShipCoordinatesService shipCoordinatesService;

    public MapController(TrackService trackService, ShipService shipService, ShipCoordinatesService shipCoordinatesService) {
        this.trackService = trackService;
        this.shipService = shipService;
        this.shipCoordinatesService = shipCoordinatesService;
    }

    @GetMapping("/")
    List<Ship> getAllShips() {
        return trackService.getAllShips();
    }

    @GetMapping("route/{shipId}")
    ShipWithRouteDTO getShipRoute(Long shipId){
        var ship = shipService.getShip(shipId);
        var route = shipCoordinatesService.getShipsCoordinates(shipId);
        return new ShipWithRouteDTO(ship, route);
    }

    @GetMapping("monitored-ships")
    List<ShipDTO> getTrackedShips(){
        return shipService.getUsersShips();
    }

    @PostMapping("monitored-ships/{shipMMSI}")
    void addShipToTrackingSystem(@PathVariable Integer shipMMSI) throws NameNotFoundException {
        shipService.addShipToTrackingSystem(shipMMSI);
    }

    @DeleteMapping("monitored-ships/{shipId}")
    void removeShipFromTrackingSystem(@PathVariable Long shipId){
        shipService.removeShipFromTrackingSystem(shipId);
    }
}
