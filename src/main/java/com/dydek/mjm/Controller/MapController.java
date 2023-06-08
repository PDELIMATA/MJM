package com.dydek.mjm.Controller;

import com.dydek.mjm.Service.TrackServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    private final TrackServiceImpl trackService;

    public MapController(TrackServiceImpl trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/map")
    public String getMap(Model model) {
        model.addAttribute("tracks", trackService.getTracks());
        return "map";
    }
}
