package com.dydek.mjm.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Datum {
    private Double latitude;
    private Double longitude;
    private String type;
    private String name;
    private Object number;
    private Object postalCode;
    private Object street;
    private Integer confidence;
    private String region;
    private String regionCode;
    private String county;
    private String locality;
    private String administrativeArea;
    private Object neighbourhood;
    private String country;
    private String countryCode;
    private String continent;
    private String label;
    private Map<String, Object> additionalProperties = new LinkedHashMap<>();

    public Datum(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
