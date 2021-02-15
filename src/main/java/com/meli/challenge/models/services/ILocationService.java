package com.meli.challenge.models.services;

import com.meli.challenge.models.Position;
import com.meli.challenge.models.exceptions.LocationException;
import com.meli.challenge.models.exceptions.SatelliteException;

public interface ILocationService {

    Position getLocation(double[] distances) throws SatelliteException, LocationException;
}