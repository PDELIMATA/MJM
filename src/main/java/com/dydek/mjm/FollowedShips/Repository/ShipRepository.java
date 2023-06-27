package com.dydek.mjm.FollowedShips.Repository;

import com.dydek.mjm.FollowedShips.Entity.Ship;
import com.dydek.mjm.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findShipsByUser(User user);

    Ship findShipByUserAndMmsi(User user, Integer mmsi);

    Ship findShipsById(Long id);
}
