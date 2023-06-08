package com.dydek.mjm.Service;

import com.dydek.mjm.Model.Datum;
import com.dydek.mjm.Model.Ship;
import com.dydek.mjm.Model.Track;

import java.util.List;

public interface TrackService {
    List<Ship> getTracks();

    List<Ship> getAllShips();

    Ship getShip(Integer mmsi);

    double getLat(Track track);

    double getLong(Track track);

    Datum getDestination(String destinationName, double Lat, double Long);
}
