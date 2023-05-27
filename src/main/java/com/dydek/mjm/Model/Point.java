package com.dydek.mjm.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Point {

    private double x;
    private double y;
    private String name;
    private Integer shipType;
    private double destinationX;
    private double destinationY;

}
