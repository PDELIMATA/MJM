package com.dydek.mjm.FollowedShips.Service;

import com.dydek.mjm.FollowedShips.DTO.ShipCoordinatesDTO;
import com.dydek.mjm.FollowedShips.Entity.ShipCoordinates;
import com.dydek.mjm.FollowedShips.Repository.ShipCoordinatesRepository;
import com.dydek.mjm.FollowedShips.Repository.ShipRepository;
import com.dydek.mjm.Service.TrackService;
import com.dydek.mjm.User.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShipCoordinatesServiceImpl implements ShipCoordinatesService {

    private final ShipCoordinatesRepository shipCoordinatesRepository;
    private final ModelMapper modelMapper;
    private final ShipRepository shipRepository;
    private final UserRepository userRepository;
    @Autowired
    @Lazy
    private TrackService trackService;

    public ShipCoordinatesServiceImpl(ShipCoordinatesRepository shipCoordinatesRepository, ModelMapper modelMapper, ShipRepository shipRepository, UserRepository userRepository) {
        this.shipCoordinatesRepository = shipCoordinatesRepository;
        this.modelMapper = modelMapper;
        this.shipRepository = shipRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ShipCoordinatesDTO> getShipsCoordinates(Long id) {
        return shipCoordinatesRepository.findById(id).stream().map(shipCoordinates -> modelMapper.map(shipCoordinates, ShipCoordinatesDTO.class)).toList();
    }

    @Override
    public void updateShipLocations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();

            var allShips = shipRepository.findShipsByUser(userRepository.findByUsername(userDetail.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("User not found")));

            var allCurrentShips = trackService.getAllShips();
            var currentShipsMap = allCurrentShips.stream()
                    .collect(Collectors.toMap(com.dydek.mjm.Model.Ship::getMmsi, Function.identity()));

            var locationsToSave = allShips.stream()
                    .flatMap(ship -> {
                        var currentShip = currentShipsMap.get(ship.getMmsi());
                        return Optional.ofNullable(currentShip).stream().map(s -> new ShipCoordinates(s.getDate(), s.getX(), s.getY(), ship));
                    })
                    .toList();

            shipCoordinatesRepository.saveAll(locationsToSave);
        }
    }
}
