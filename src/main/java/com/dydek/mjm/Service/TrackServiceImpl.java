package com.dydek.mjm.Service;

import com.dydek.mjm.FollowedShips.Service.ShipCoordinatesService;
import com.dydek.mjm.Model.Datum;
import com.dydek.mjm.Model.Ship;
import com.dydek.mjm.Model.Track;
import com.dydek.mjm.Service.ApiService.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TrackServiceImpl implements TrackService {
    RestTemplate restTemplate = new RestTemplate();
    private final ApiService apiService;
    private final ShipCoordinatesService shipCoordinatesService;
    private List<Ship> shipsOnRadar = new ArrayList<>();

    public TrackServiceImpl(ApiService apiService, ShipCoordinatesService shipCoordinatesService) {
        this.apiService = apiService;
        this.shipCoordinatesService = shipCoordinatesService;
    }

    @Override
    public List<Ship> getTracks() {

        HttpHeaders httpHeaders = new HttpHeaders();
        String token = apiService.getToken();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Track[]> exchange = restTemplate.exchange(
                "https://live.ais.barentswatch.no/live/v1/latest/combined?modelType=Full&modelFormat=Json",
                HttpMethod.GET,
                httpEntity,
                Track[].class
        );

        //TODO: fix filtering for area
        Track[] tracks = Arrays.stream(Objects.requireNonNull(exchange.getBody()))
                .map(track -> {
                    if (track.getName() == null) {
                        track.setName("UNKNOWN");
                    }
                    if (track.getDestination() == null) {
                        track.setDestination("UNKNOWN");
                    }
                    return track;
                })
                .limit(50)
                .toArray(Track[]::new);

        return this.shipsOnRadar = Arrays.stream(tracks)
                .map(track -> new Ship(
                        track.getLatitude(),
                        track.getLongitude(),
                        track.getName(),
                        track.getMmsi(),
                        track.getShipType(),
                        getLat(track),
                        getLong(track),
                        new Date()
                ))
                .toList();
    }

    @Override
    public List<Ship> getAllShips() {
        return shipsOnRadar;
    }

    @Override
    public Ship getShip(Integer mmsi) {
        return shipsOnRadar.stream()
                .filter(s -> s.getMmsi().equals(mmsi))
                .findFirst()
                .orElse(null);
    }

    @Scheduled(initialDelay = 300, fixedDelay = 60_000)
    public void fetchCurrentShips() {
        this.shipsOnRadar = getTracks();
        this.shipCoordinatesService.updateShipLocations();
    }

    @Override
    public double getLat(Track track) {
        return getDestination(track.getDestination(), track.getLatitude(), track.getLongitude()).getLatitude();
    }

    @Override
    public double getLong(Track track) {
        return getDestination(track.getDestination(), track.getLatitude(), track.getLongitude()).getLongitude();
    }

    @Override
    public Datum getDestination(String destinationName, double Lat, double Long) {
        try {
            String url = "http://api.positionstack.com/v1/forward?access_key=ecc21c99187c197c45144b0a89cd6648&query=" + destinationName;
            JsonNode data = Objects.requireNonNull(restTemplate.getForObject(url, JsonNode.class)).get("data").get(0);
            double latitude = Optional.of(data.get("latitude").asDouble()).orElse(Lat);
            double longitude = Optional.of(data.get("longitude").asDouble()).orElse(Long);
            return new Datum(latitude, longitude);
        } catch (Exception ex) {
            return new Datum(Lat, Long);
        }
    }
}
