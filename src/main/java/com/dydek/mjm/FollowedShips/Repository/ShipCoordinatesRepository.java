package com.dydek.mjm.FollowedShips.Repository;

import com.dydek.mjm.FollowedShips.Entity.ShipCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipCoordinatesRepository extends JpaRepository<ShipCoordinates, Long> {
}
