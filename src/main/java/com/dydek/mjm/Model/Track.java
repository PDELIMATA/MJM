package com.dydek.mjm.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Track {

    @JsonProperty("mmsi")
    private Integer mmsi;
    @JsonProperty("msgtime")
    private String msgtime;
    @JsonProperty("altitude")
    private Object altitude;
    @JsonProperty("courseOverGround")
    private Object courseOverGround;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("navigationalStatus")
    private Integer navigationalStatus;
    @JsonProperty("rateOfTurn")
    private Object rateOfTurn;
    @JsonProperty("speedOverGround")
    private Double speedOverGround;
    @JsonProperty("trueHeading")
    private Object trueHeading;
    @JsonProperty("imoNumber")
    private Object imoNumber;
    @JsonProperty("callSign")
    private Object callSign;
    @JsonProperty("name")
    private String name;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("eta")
    private String eta;
    @JsonProperty("draught")
    private Object draught;
    @JsonProperty("shipLength")
    private Object shipLength;
    @JsonProperty("shipWidth")
    private Object shipWidth;
    @JsonProperty("shipType")
    private Integer shipType;
    @JsonProperty("dimensionA")
    private Object dimensionA;
    @JsonProperty("dimensionB")
    private Object dimensionB;
    @JsonProperty("dimensionC")
    private Object dimensionC;
    @JsonProperty("dimensionD")
    private Object dimensionD;
    @JsonProperty("positionFixingDeviceType")
    private Integer positionFixingDeviceType;
    @JsonProperty("reportClass")
    private String reportClass;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

}
