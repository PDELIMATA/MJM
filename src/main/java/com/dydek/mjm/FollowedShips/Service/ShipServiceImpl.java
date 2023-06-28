package com.dydek.mjm.FollowedShips.Service;

import com.dydek.mjm.FollowedShips.DTO.ShipCoordinatesDTO;
import com.dydek.mjm.FollowedShips.DTO.ShipDTO;
import com.dydek.mjm.FollowedShips.DTO.ShipWithCoordinatesDTO;
import com.dydek.mjm.FollowedShips.DTO.ShipWithRouteDTO;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {
    private final UserRepository userRepository;
    private final ShipRepository shipRepository;
    private final ShipCoordinatesRepository shipCoordinatesRepository;
    private final ModelMapper modelMapper;
    private final TrackService trackService;

    private final ShipCoordinatesService shipCoordinatesService;

    public ShipServiceImpl(UserRepository userRepository, ShipRepository shipRepository, ShipCoordinatesRepository shipCoordinatesRepository, ModelMapper modelMapper, TrackService trackService, ShipCoordinatesService shipCoordinatesService) {
        this.userRepository = userRepository;
        this.shipRepository = shipRepository;
        this.shipCoordinatesRepository = shipCoordinatesRepository;
        this.modelMapper = modelMapper;
        this.trackService = trackService;
        this.shipCoordinatesService = shipCoordinatesService;
    }

    @Override
    public List<Ship> getUsersShips(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        return shipRepository.findShipsByUser(user);
    }

    @Override
    @Transactional
    public List<ShipWithRouteDTO> getUserShipsWithRoutes(String username) {
        List<ShipWithRouteDTO> userShipsWithRoute = new ArrayList<>();
        getUsersShips(username).forEach(ship -> {
            var userShip = this.getShip(ship.getId());
            var route = shipCoordinatesService.getShipsCoordinates(ship.getId());
            userShipsWithRoute.add(new ShipWithRouteDTO(userShip, route));
        });
        return userShipsWithRoute;
    }

    @Override
    public List<ShipDTO> getMmsiShipAddedToTS(String username) {
        return getUsersShips(username).stream()
                .map(ship -> modelMapper.map(ship, ShipDTO.class))
                .toList();
    }

    @Override
    public ShipWithCoordinatesDTO getShip(Long id) {
        Ship ship = shipRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ShipDTO shipDTO = modelMapper.map(ship, ShipDTO.class);
        ShipCoordinatesDTO shipCoordinatesDTO = modelMapper.map(shipCoordinatesRepository.findById(id).orElse(new ShipCoordinates()), ShipCoordinatesDTO.class);
        return new ShipWithCoordinatesDTO(shipDTO, shipCoordinatesDTO);
    }

    @Override
    @Transactional
    public void addShipToTrackingSystem(String username, Integer mmsi) throws NameNotFoundException, EntityExistsException {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        Ship existingShip = shipRepository.findShipByUserAndMmsi(user, mmsi);
        if (existingShip != null) {
            throw new EntityExistsException("Ship already exists for the user.");
        }

        com.dydek.mjm.Model.Ship serviceShip = trackService.getShip(mmsi);
        if (serviceShip == null) {
            throw new NameNotFoundException("There is no Ship with this MMSI");
        }

        Ship ship = new Ship(mmsi, serviceShip.getShipType(), serviceShip.getName(), user, true);
        shipRepository.save(ship);

        ShipCoordinates shipCoordinates = new ShipCoordinates(
                serviceShip.getDate(),
                serviceShip.getX(),
                serviceShip.getY(),
                ship);

        shipCoordinatesRepository.save(shipCoordinates);
    }

    @Override
    @Transactional
    public void removeShipFromTrackingSystem(String username, Long shipId) {
        Ship ship = shipRepository.findById(shipId).orElseThrow(EntityNotFoundException::new);
        if (!ship.getUser().getUsername().equals(username)) {
            throw new AuthorizationServiceException("User is not authorized to remove this ship.");
        }
        shipRepository.delete(ship);
    }
}
