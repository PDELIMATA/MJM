package com.dydek.mjm.FollowedShips.Service;

import com.dydek.mjm.FollowedShips.DTO.ShipCoordinatesDTO;
import com.dydek.mjm.FollowedShips.DTO.ShipDTO;
import com.dydek.mjm.FollowedShips.Entity.Ship;
import com.dydek.mjm.FollowedShips.Entity.ShipCoordinates;
import com.dydek.mjm.FollowedShips.Repository.ShipCoordinatesRepository;
import com.dydek.mjm.FollowedShips.Repository.ShipRepository;
import com.dydek.mjm.Service.TrackService;
import com.dydek.mjm.User.Entity.User;
import com.dydek.mjm.User.Repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import javax.naming.NameNotFoundException;
import java.util.List;

public class ShipServiceImpl implements ShipService {
    private final UserRepository userRepository;
    private final ShipRepository shipRepository;
    private final ShipCoordinatesRepository shipCoordinatesRepository;
    private final ModelMapper modelMapper;
    private final TrackService trackService;

    public ShipServiceImpl(UserRepository userRepository, ShipRepository shipRepository, ShipCoordinatesRepository shipCoordinatesRepository, ModelMapper modelMapper, TrackService trackService) {
        this.userRepository = userRepository;
        this.shipRepository = shipRepository;
        this.shipCoordinatesRepository = shipCoordinatesRepository;
        this.modelMapper = modelMapper;
        this.trackService = trackService;
    }

    @Override
    public List<ShipDTO> getUsersShips(String username) {
        return shipRepository.findShipsByUser(userRepository.findByUsername(username).orElse(new User())).stream().map(ship -> modelMapper.map(ship, ShipDTO.class)).toList();
    }

    @Override
    public List<ShipCoordinatesDTO> getShipsCoordinates(Long id) {
        return shipCoordinatesRepository.findById(id).stream().map(shipCoordinates -> modelMapper.map(shipCoordinates, ShipCoordinatesDTO.class)).toList();
    }

    @Override
    public ShipDTO getShip(Long id) {
        return modelMapper.map(shipRepository.findById(id), ShipDTO.class);
    }

    //TODO: Add coordinates adding during adding Ship to TS
    @Override
    @Transactional
    public void addShipToTrackingSystem(String username, Integer mmsi) throws NameNotFoundException {
        var userEntity = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);


        if (shipRepository.existsByUserAndMmsi(userRepository.findByUsername(username).get(), mmsi)) {
            throw new EntityExistsException();
        }

        ShipDTO shipDTO = modelMapper.map(trackService.getShip(mmsi), ShipDTO.class);

        if (shipDTO == null) {
            throw new NameNotFoundException("There is no ship with this MMSI");
        } else {
            Ship ship = new Ship(mmsi, shipDTO.getShipType(), shipDTO.getName(), userEntity);
            shipRepository.save(ship);

        }
    }

    //TODO: Add removing ship from TS
    @Override
    public void removeShipFromTrackingSystem(String username, Long shipPublicId) {

    }
}
