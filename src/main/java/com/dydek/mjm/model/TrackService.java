package com.dydek.mjm.model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackService {

    public List<Point> getTracks() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = new String("eyJhbGciOiJSUzI1NiIsImtpZCI6IjBCM0I1NEUyRkQ5OUZCQkY5NzVERDMxNDBDREQ4OEI1QzA5RkFDRjNSUzI1NiIsIng1dCI6IkN6dFU0djJaLTctWFhkTVVETjJJdGNDZnJQTSIsInR5cCI6ImF0K2p3dCJ9.eyJpc3MiOiJodHRwczovL2lkLmJhcmVudHN3YXRjaC5ubyIsIm5iZiI6MTY4NTExNzIzOSwiaWF0IjoxNjg1MTE3MjM5LCJleHAiOjE2ODUxMjA4MzksImF1ZCI6ImFpcyIsInNjb3BlIjpbImFpcyJdLCJjbGllbnRfaWQiOiJkZWxpbWF0YS5wc0BnbWFpbC5jb206ZHlkZWsifQ.oPX7Gwot_ktjK8xTGAt9g1g8Ysj56htIbHSoBO3VS0O79bbllAYOxc5yxXr79B58vpAO6GfrDg-zyFWsK_bs2HrGOyLORZEXmC_CXkZP5aPQD7pr9I2lgpv-AKqHcZwDlX2eFniFdB8kOAEtmLjyM7DnryWkN-ncnnY_JCalysldxUoyUgFyg4Pkx9zwgB2XBL6zLiHVDxnPbMtQ5Asf4J1Wy4rQjXXKP0OGD3F43KVIaDULfGskqh3yhFd3fbuYXsmBg8EtDGplXeN1b9iSXhgt3iXTrC_pc34ugl5elL4MKIvyQ6VDd7WY0hsy8lKnJ3rWv28YpswjQMQyfBkzQUZUWQnZtf04lsBnIjxLjwP-kZQ8xosGDTD4KVFYI73dljB-Ur2Ucq_PeuEcutzsKogsaP0X2KvKzyyN8MgFkVJhQY0VSR9nNo2IVV44LMqT6d43l2uWl_BKzWEhC6cVJYqIchHaZru2oWYsN0mxgYc_1mfpdu_k_yd4NiDxM_4pROzpOOTIiFlV9LqGGKldhzd3P3AueX2yt07mpIMT08F5AYxkRZU8GnlX1yC0cWgqBVbNBTxIKdLSNWJMUJAnIer5Q3lXy8JW7mhAuUZgNSGo7TY_au2tJnBeZs6dfIFCA_I2wWp__CPLhgxp-kVItwUYWDbqUdrAveptK5Td2fk");
        httpHeaders.add("Authorization", "Bearer "+token); // Dodaj swój token tutaj
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Track[]> exchange = restTemplate.exchange(
                "https://live.ais.barentswatch.no/live/v1/latest/combined?modelType=Full&modelFormat=Json",
                HttpMethod.GET,
                httpEntity,
                Track[].class
        );

        Track[] tracks = Arrays.stream(exchange.getBody())
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
                        track.getDestination().toString()
                ))
                .collect(Collectors.toList());
    }
}
