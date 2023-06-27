package com.dydek.mjm.Controller;

import com.dydek.mjm.FollowedShips.DTO.ShipWithRouteDTO;
import com.dydek.mjm.FollowedShips.Service.ShipCoordinatesService;
import com.dydek.mjm.FollowedShips.Service.ShipService;
import com.dydek.mjm.Service.TrackService;
import com.dydek.mjm.User.Entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;

@Controller
public class MapController {

    private final TrackService trackService;
    private final ShipService shipService;
    private final ShipCoordinatesService shipCoordinatesService;

    public MapController(TrackService trackService, ShipService shipService, ShipCoordinatesService shipCoordinatesService) {
        this.trackService = trackService;
        this.shipService = shipService;
        this.shipCoordinatesService = shipCoordinatesService;
    }

    @GetMapping("/all")
    String getAllShips(Model model, @AuthenticationPrincipal User authenticationUser) {
        model.addAttribute("tracks", trackService.getAllShips());
        model.addAttribute("userShips", shipService.getMmsiShipAddedToTS(authenticationUser.getUsername()));
        return "map";
    }

    @GetMapping("route/{shipId}")
    String getShipRoute(Model model, @PathVariable Long shipId) {
        var ship = shipService.getShip(shipId);
        var route = shipCoordinatesService.getShipsCoordinates(shipId);
        model.addAttribute("shipCoordinates", ship.getShipCoordinatesDTO());
        model.addAttribute("ship", ship.getShipDTO());
        model.addAttribute("shipRoute", new ShipWithRouteDTO(ship, route));
        return "route";
    }

    @GetMapping("monitored-ships/routes")
    String getTrackedShipsRoutes(Model model, @AuthenticationPrincipal User authenticationUser) {
        model.addAttribute("shipsWithRoutes", shipService.getUserShipsWithRoutes(authenticationUser.getUsername()));
        return "all-ship-routes";
    }

    @GetMapping("monitored-ships/list")
    String getTrackedShipsList(Model model, @AuthenticationPrincipal User authenticationUser) {
        model.addAttribute("ships", shipService.getUsersShips(authenticationUser.getUsername()));
        return "all-ship-list";
    }

    @PostMapping("monitored-ships/{shipMMSI}")
    String addShipToTrackingSystem(@PathVariable Integer shipMMSI, @AuthenticationPrincipal User authenticationUser) throws NameNotFoundException {
        shipService.addShipToTrackingSystem(authenticationUser.getUsername(), shipMMSI);
        return "redirect:/all";
    }

    @RequestMapping(value = "monitored-ships/{shipId}", method = RequestMethod.GET)
    String removeShipFromTrackingSystem(@PathVariable Long shipId, @AuthenticationPrincipal User authenticationUser) {
        shipService.removeShipFromTrackingSystem(authenticationUser.getUsername(), shipId);
        return "redirect:/all";
    }
}