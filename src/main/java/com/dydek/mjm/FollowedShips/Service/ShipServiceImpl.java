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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Optional;

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

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetail = (UserDetails) auth.getPrincipal();

    @Override
    public List<ShipDTO> getUsersShips() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        return shipRepository.findShipsByUser(userRepository.findByUsername(userDetail.getUsername()).orElse(new User())).stream().map(ship -> modelMapper.map(ship, ShipDTO.class)).toList();
    }

    @Override
    public ShipDTO getShip(Long id) {
        return modelMapper.map(shipRepository.findById(id), ShipDTO.class);
    }

    @Override
    @Transactional
    public void addShipToTrackingSystem(Integer mmsi) throws NameNotFoundException, EntityExistsException {
        var userEntity = userRepository.findByUsername(userDetail.getUsername())
                .orElseThrow(EntityNotFoundException::new);

        Ship existingShip = shipRepository.findShipByUserAndMmsi(userEntity, mmsi);

        Optional.ofNullable(existingShip)
                .ifPresent(ship -> {
                    throw new EntityExistsException();
                });

        com.dydek.mjm.Model.Ship serviceShip = trackService.getShip(mmsi);

        Optional.ofNullable(serviceShip)
                .orElseThrow(() -> new NameNotFoundException("There is no serviceShip with this MMSI"));

        Ship ship = new Ship(mmsi, serviceShip.getShipType(), serviceShip.getName(), userEntity);
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
    public void removeShipFromTrackingSystem(Long shipId) {
        var ship = shipRepository.findById(shipId).orElseThrow();
        if (!ship.getUser().getUsername().equals(userDetail.getUsername())) {
            throw new AuthorizationServiceException("");
        }
        shipRepository.delete(ship);
    }
}
