package com.dydek.mjm.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Ship {

    private double x;
    private double y;
    private String name;
    private Integer mmsi;
    private Integer shipType;
    private double destinationX;
    private double destinationY;
    private Date date;

}
