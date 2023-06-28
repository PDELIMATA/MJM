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
    public String getAllShips(Model model, @AuthenticationPrincipal User authenticationUser) {
        model.addAttribute("tracks", trackService.getAllShips());
        model.addAttribute("userShips", shipService.getMmsiShipAddedToTS(authenticationUser.getUsername()));
        return "map";
    }

    @GetMapping("/route/{shipId}")
    public String getShipRoute(Model model, @PathVariable("shipId") Long shipId) {
        var ship = shipService.getShip(shipId);
        var route = shipCoordinatesService.getShipsCoordinates(shipId);
        model.addAttribute("shipCoordinates", ship.getShipCoordinatesDTO())
                .addAttribute("ship", ship.getShipDTO())
                .addAttribute("shipRoute", new ShipWithRouteDTO(ship, route));
        return "route";
    }

    @GetMapping("/monitored-ships/routes")
    public String getTrackedShipsRoutes(Model model, @AuthenticationPrincipal User authenticationUser) {
        model.addAttribute("shipsWithRoutes", shipService.getUserShipsWithRoutes(authenticationUser.getUsername()));
        return "all-ship-routes";
    }

    @GetMapping("/monitored-ships/ship/{shipId}")
    public String getTrackedShip(Model model, @PathVariable("shipId") Long shipId) {
        model.addAttribute("ship", shipService.getShip(shipId));
        return "ship";
    }

    @GetMapping("/monitored-ships/list")
    public String getTrackedShipsList(Model model, @AuthenticationPrincipal User authenticationUser) {
        model.addAttribute("ships", shipService.getUsersShips(authenticationUser.getUsername()));
        return "all-ship-list";
    }

    @PostMapping("/monitored-ships/{shipMMSI}")
    public String addShipToTrackingSystem(@PathVariable("shipMMSI") Integer shipMMSI, @AuthenticationPrincipal User authenticationUser) throws NameNotFoundException {
        shipService.addShipToTrackingSystem(authenticationUser.getUsername(), shipMMSI);
        return "redirect:/all";
    }

    @RequestMapping(value = "/monitored-ships/{shipId}", method = RequestMethod.GET)
    public String removeShipFromTrackingSystem(@PathVariable("shipId") Long shipId, @AuthenticationPrincipal User authenticationUser) {
        shipService.removeShipFromTrackingSystem(authenticationUser.getUsername(), shipId);
        return "redirect:/all";
    }
}
