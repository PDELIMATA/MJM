package com.dydek.mjm.FollowedShips.Repository;

import com.dydek.mjm.FollowedShips.Entity.Ship;
import com.dydek.mjm.FollowedShips.Entity.ShipCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface ShipCoordinatesRepository extends JpaRepository<ShipCoordinates, Long> {

    Stream<ShipCoordinates> findShipCoordinatesByShip(Ship ship);
}
