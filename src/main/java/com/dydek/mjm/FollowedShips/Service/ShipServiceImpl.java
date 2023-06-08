package com.dydek.mjm.FollowedShips.Service;

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
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;

@Service
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
    public ShipDTO getShip(Long id) {
        return modelMapper.map(shipRepository.findById(id), ShipDTO.class);
    }


    @Override
    @Transactional
    public void addShipToTrackingSystem(String username, Integer mmsi) throws NameNotFoundException {
        var userEntity = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);

        if (shipRepository.existsByUserAndMmsi(userRepository.findByUsername(username).get(), mmsi)) {
            throw new EntityExistsException();
        }

        com.dydek.mjm.Model.Ship serviceShip = trackService.getShip(mmsi);

        if (serviceShip == null) {
            throw new NameNotFoundException("There is no serviceShip with this MMSI");
        } else {
            Ship ship = new Ship(mmsi, serviceShip.getShipType(), serviceShip.getName(), userEntity);
            shipRepository.save(ship);

            ShipCoordinates shipCoordinates = new ShipCoordinates(
                    serviceShip.getDate(),
                    serviceShip.getX(),
                    serviceShip.getY(),
                    ship);

            shipCoordinatesRepository.save(shipCoordinates);
        }
    }

    @Override
    @Transactional
    public void removeShipFromTrackingSystem(String username, Long shipId) {
        var ship = shipRepository.findById(shipId).orElseThrow();
        if (!ship.getUser().getUsername().equals(username)) {
            throw new AuthorizationServiceException("");
        }
        shipRepository.delete(ship);
    }
}
