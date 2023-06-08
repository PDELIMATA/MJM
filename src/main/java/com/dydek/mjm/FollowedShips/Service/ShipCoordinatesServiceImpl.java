package com.dydek.mjm.FollowedShips.Service;

import com.dydek.mjm.FollowedShips.DTO.ShipCoordinatesDTO;
import com.dydek.mjm.FollowedShips.Entity.ShipCoordinates;
import com.dydek.mjm.FollowedShips.Repository.ShipCoordinatesRepository;
import com.dydek.mjm.FollowedShips.Repository.ShipRepository;
import com.dydek.mjm.Model.Ship;
import com.dydek.mjm.Service.TrackService;
import com.dydek.mjm.User.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ShipCoordinatesServiceImpl implements ShipCoordinatesService{

    private final ShipCoordinatesRepository shipCoordinatesRepository;
    private final ModelMapper modelMapper;
    private final ShipRepository shipRepository;
    private final UserRepository userRepository;
    private final TrackService trackService;

    public ShipCoordinatesServiceImpl(ShipCoordinatesRepository shipCoordinatesRepository, ModelMapper modelMapper, ShipRepository shipRepository, UserRepository userRepository, TrackService trackService) {
        this.shipCoordinatesRepository = shipCoordinatesRepository;
        this.modelMapper = modelMapper;
        this.shipRepository = shipRepository;
        this.userRepository = userRepository;
        this.trackService = trackService;
    }

    @Override
    public List<ShipCoordinatesDTO> getShipsCoordinates(Long id) {
        return shipCoordinatesRepository.findById(id).stream().map(shipCoordinates -> modelMapper.map(shipCoordinates, ShipCoordinatesDTO.class)).toList();
    }
    @Override
    public void updateShipLocations(String username) {
        var allShips = shipRepository.findShipsByUser(userRepository.findByUsername(username).get());
        var allCurrentShips = trackService.getAllShips();
        var currentShipsMap = new HashMap<Integer, Ship>();
        allCurrentShips.forEach(s -> currentShipsMap.put(s.getMmsi(), s));

        var locationsToSave = new ArrayList<ShipCoordinates>();
        for (var ship : allShips) {
            var currentShip = currentShipsMap.get(ship.getMmsi());
            if (currentShip != null) {
                locationsToSave.add(new ShipCoordinates(currentShip.getDate(), currentShip.getX(), currentShip.getY(), ship));
            }
        }

        shipCoordinatesRepository.saveAll(locationsToSave);
    }
}
