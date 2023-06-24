package com.dydek.mjm.FollowedShips.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipDTO {
    private Long id;
    private Integer mmsi;
    private String shipType;
    private String name;
    private boolean aaddedToTrackingSystem;

}
