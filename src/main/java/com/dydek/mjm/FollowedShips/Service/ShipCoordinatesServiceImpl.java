package com.dydek.mjm.FollowedShips.Service;

import com.dydek.mjm.FollowedShips.DTO.ShipCoordinatesDTO;
import com.dydek.mjm.FollowedShips.Entity.Ship;
import com.dydek.mjm.FollowedShips.Entity.ShipCoordinates;
import com.dydek.mjm.FollowedShips.Repository.ShipCoordinatesRepository;
import com.dydek.mjm.FollowedShips.Repository.ShipRepository;
import com.dydek.mjm.Service.TrackService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShipCoordinatesServiceImpl implements ShipCoordinatesService {

    private final ShipCoordinatesRepository shipCoordinatesRepository;
    private final ModelMapper modelMapper;
    private final ShipRepository shipRepository;
    @Autowired
    @Lazy
    private TrackService trackService;

    public ShipCoordinatesServiceImpl(ShipCoordinatesRepository shipCoordinatesRepository, ModelMapper modelMapper, ShipRepository shipRepository) {
        this.shipCoordinatesRepository = shipCoordinatesRepository;
        this.modelMapper = modelMapper;
        this.shipRepository = shipRepository;
    }

    @Override
    @Transactional
    public List<ShipCoordinatesDTO> getShipsCoordinates(Long id) {
        try (Stream<ShipCoordinates> shipCoordinatesStream = shipCoordinatesRepository.findShipCoordinatesByShip(shipRepository.findShipsById(id))) {
            return shipCoordinatesStream
                    .map(shipCoordinates -> modelMapper.map(shipCoordinates, ShipCoordinatesDTO.class))
                    .toList();
        }
    }

    @Override
    public void updateShipLocations() {
        List<com.dydek.mjm.Model.Ship> allCurrentShips = trackService.getAllShips();
        var currentShipsMap = allCurrentShips.stream()
                .collect(Collectors.toMap(com.dydek.mjm.Model.Ship::getMmsi, Function.identity()));

        var locationsToSave = shipRepository.findAll().stream()
                .flatMap(ship -> Optional.ofNullable(currentShipsMap.get(ship.getMmsi())).map(s -> createShipCoordinates(s, ship)).stream())
                .toList();

        shipCoordinatesRepository.saveAll(locationsToSave);
    }

    private ShipCoordinates createShipCoordinates(com.dydek.mjm.Model.Ship currentShip, Ship ship) {
        return new ShipCoordinates(currentShip.getDate(), currentShip.getX(), currentShip.getY(), ship);
    }
}