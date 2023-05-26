package com.dydek.mjm.model;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TrackService {
    RestTemplate restTemplate = new RestTemplate();

    public List<Point> getTracks() {

        HttpHeaders httpHeaders = new HttpHeaders();
        String token = "token";
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Track[]> exchange = restTemplate.exchange(
                "https://live.ais.barentswatch.no/live/v1/latest/combined?modelType=Full&modelFormat=Json",
                HttpMethod.GET,
                httpEntity,
                Track[].class
        );

        //TODO: fix filtering for area instead of ShipType and Name
        Track[] tracks = Arrays.stream(Objects.requireNonNull(exchange.getBody()))
                .filter(track -> track.getShipType() == 30)
                .filter(track -> track.getName() != null && track.getName().startsWith("H"))
                .map(track -> {
                    if (track.getName() == null) {
                        track.setName("UNKNOWN");
                    }
                    if (track.getDestination() == null) {
                        track.setDestination("UNKNOWN");
                    }
                    return track;
                })
                .toArray(Track[]::new);

        return Arrays.stream(tracks)
                .map(track -> new Point(
                        track.getLatitude(),
                        track.getLongitude(),
                        track.getName(),
                        track.getShipType(),
                        getLat(track),
                        getLong(track)
                ))
                .toList();
    }

    private double getLat(Track track) {
        return getDestination(track.getDestination(), track.getLatitude(), track.getLongitude()).getLatitude();
    }

    private double getLong(Track track) {
         return getDestination(track.getDestination(), track.getLatitude(), track.getLongitude()).getLongitude();
    }

    public Datum getDestination(String destinationName, double Lat, double Long) {
        try {
            String url = "<positionstockapi>" + destinationName;
            JsonNode data = Objects.requireNonNull(restTemplate.getForObject(url, JsonNode.class)).get("data").get(0);
            double latitude = Optional.of(data.get("latitude").asDouble()).orElse(Lat);
            double longitude = Optional.of(data.get("longitude").asDouble()).orElse(Long);
            return new Datum(latitude, longitude);
        } catch (Exception ex) {
            return new Datum(Lat, Long);
        }
    }


}
