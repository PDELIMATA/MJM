package com.dydek.mjm.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "mmsi",
        "msgtime",
        "altitude",
        "courseOverGround",
        "latitude",
        "longitude",
        "navigationalStatus",
        "rateOfTurn",
        "speedOverGround",
        "trueHeading",
        "imoNumber",
        "callSign",
        "name",
        "destination",
        "eta",
        "draught",
        "shipLength",
        "shipWidth",
        "shipType",
        "dimensionA",
        "dimensionB",
        "dimensionC",
        "dimensionD",
        "positionFixingDeviceType",
        "reportClass"
})
@Generated("jsonschema2pojo")
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

    @JsonProperty("mmsi")
    public Integer getMmsi() {
        return mmsi;
    }

    @JsonProperty("mmsi")
    public void setMmsi(Integer mmsi) {
        this.mmsi = mmsi;
    }

    @JsonProperty("msgtime")
    public String getMsgtime() {
        return msgtime;
    }

    @JsonProperty("msgtime")
    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    @JsonProperty("altitude")
    public Object getAltitude() {
        return altitude;
    }

    @JsonProperty("altitude")
    public void setAltitude(Object altitude) {
        this.altitude = altitude;
    }

    @JsonProperty("courseOverGround")
    public Object getCourseOverGround() {
        return courseOverGround;
    }

    @JsonProperty("courseOverGround")
    public void setCourseOverGround(Object courseOverGround) {
        this.courseOverGround = courseOverGround;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("navigationalStatus")
    public Integer getNavigationalStatus() {
        return navigationalStatus;
    }

    @JsonProperty("navigationalStatus")
    public void setNavigationalStatus(Integer navigationalStatus) {
        this.navigationalStatus = navigationalStatus;
    }

    @JsonProperty("rateOfTurn")
    public Object getRateOfTurn() {
        return rateOfTurn;
    }

    @JsonProperty("rateOfTurn")
    public void setRateOfTurn(Object rateOfTurn) {
        this.rateOfTurn = rateOfTurn;
    }

    @JsonProperty("speedOverGround")
    public Double getSpeedOverGround() {
        return speedOverGround;
    }

    @JsonProperty("speedOverGround")
    public void setSpeedOverGround(Double speedOverGround) {
        this.speedOverGround = speedOverGround;
    }

    @JsonProperty("trueHeading")
    public Object getTrueHeading() {
        return trueHeading;
    }

    @JsonProperty("trueHeading")
    public void setTrueHeading(Object trueHeading) {
        this.trueHeading = trueHeading;
    }

    @JsonProperty("imoNumber")
    public Object getImoNumber() {
        return imoNumber;
    }

    @JsonProperty("imoNumber")
    public void setImoNumber(Object imoNumber) {
        this.imoNumber = imoNumber;
    }

    @JsonProperty("callSign")
    public Object getCallSign() {
        return callSign;
    }

    @JsonProperty("callSign")
    public void setCallSign(Object callSign) {
        this.callSign = callSign;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("destination")
    public String getDestination() {
        return destination;
    }

    @JsonProperty("destination")
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @JsonProperty("eta")
    public String getEta() {
        return eta;
    }

    @JsonProperty("eta")
    public void setEta(String eta) {
        this.eta = eta;
    }

    @JsonProperty("draught")
    public Object getDraught() {
        return draught;
    }

    @JsonProperty("draught")
    public void setDraught(Object draught) {
        this.draught = draught;
    }

    @JsonProperty("shipLength")
    public Object getShipLength() {
        return shipLength;
    }

    @JsonProperty("shipLength")
    public void setShipLength(Object shipLength) {
        this.shipLength = shipLength;
    }

    @JsonProperty("shipWidth")
    public Object getShipWidth() {
        return shipWidth;
    }

    @JsonProperty("shipWidth")
    public void setShipWidth(Object shipWidth) {
        this.shipWidth = shipWidth;
    }

    @JsonProperty("shipType")
    public Integer getShipType() {
        return shipType;
    }

    @JsonProperty("shipType")
    public void setShipType(Integer shipType) {
        this.shipType = shipType;
    }

    @JsonProperty("dimensionA")
    public Object getDimensionA() {
        return dimensionA;
    }

    @JsonProperty("dimensionA")
    public void setDimensionA(Object dimensionA) {
        this.dimensionA = dimensionA;
    }

    @JsonProperty("dimensionB")
    public Object getDimensionB() {
        return dimensionB;
    }

    @JsonProperty("dimensionB")
    public void setDimensionB(Object dimensionB) {
        this.dimensionB = dimensionB;
    }

    @JsonProperty("dimensionC")
    public Object getDimensionC() {
        return dimensionC;
    }

    @JsonProperty("dimensionC")
    public void setDimensionC(Object dimensionC) {
        this.dimensionC = dimensionC;
    }

    @JsonProperty("dimensionD")
    public Object getDimensionD() {
        return dimensionD;
    }

    @JsonProperty("dimensionD")
    public void setDimensionD(Object dimensionD) {
        this.dimensionD = dimensionD;
    }

    @JsonProperty("positionFixingDeviceType")
    public Integer getPositionFixingDeviceType() {
        return positionFixingDeviceType;
    }

    @JsonProperty("positionFixingDeviceType")
    public void setPositionFixingDeviceType(Integer positionFixingDeviceType) {
        this.positionFixingDeviceType = positionFixingDeviceType;
    }

    @JsonProperty("reportClass")
    public String getReportClass() {
        return reportClass;
    }

    @JsonProperty("reportClass")
    public void setReportClass(String reportClass) {
        this.reportClass = reportClass;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }



}
