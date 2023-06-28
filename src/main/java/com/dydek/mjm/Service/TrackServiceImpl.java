package com.dydek.mjm.Service;

import com.dydek.mjm.FollowedShips.Service.ShipCoordinatesService;
import com.dydek.mjm.Model.Datum;
import com.dydek.mjm.Model.Ship;
import com.dydek.mjm.Model.Track;
import com.dydek.mjm.Service.ApiService.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class TrackServiceImpl implements TrackService {
    private final WebClient webClient;
    private final ApiService apiService;
    private final ShipCoordinatesService shipCoordinatesService;
    private List<Ship> shipsOnRadar = new ArrayList<>();

    public TrackServiceImpl(ApiService apiService, ShipCoordinatesService shipCoordinatesService) {
        this.apiService = apiService;
        this.shipCoordinatesService = shipCoordinatesService;
        this.webClient = WebClient.builder().build();
    }

    @Override
    public List<Ship> getTracks() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = apiService.getToken();
        httpHeaders.setBearerAuth(token);

        return webClient.get()
                .uri("https://live.ais.barentswatch.no/live/v1/latest/combined?modelType=Full&modelFormat=Json")
                .headers(headers -> headers.addAll(httpHeaders))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Track.class)
                .take(100)
                .map(this::mapToShip)
                .collectList()
                .block();
    }

    private Ship mapToShip(Track track) {
        if (track.getName() == null) {
            track.setName("UNKNOWN");
        }
        if (track.getDestination() == null) {
            track.setDestination("UNKNOWN");
        }
        return new Ship(
                track.getLatitude(),
                track.getLongitude(),
                track.getName(),
                track.getMmsi(),
                track.getShipType(),
                getLat(track),
                getLong(track),
                new Date()
        );
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
            JsonNode data = Objects.requireNonNull(webClient.get().uri(url).retrieve().bodyToMono(JsonNode.class).block())
                    .get("data")
                    .get(0);

            double latitude = Optional.ofNullable(data.get("latitude"))
                    .map(JsonNode::asDouble)
                    .orElse(Lat);
            double longitude = Optional.ofNullable(data.get("longitude"))
                    .map(JsonNode::asDouble)
                    .orElse(Long);

            return new Datum(latitude, longitude);
        } catch (Exception ex) {
            return new Datum(Lat, Long);
        }
    }
}
