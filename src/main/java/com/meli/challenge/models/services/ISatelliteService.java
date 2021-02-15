package com.meli.challenge.models.services;

import com.meli.challenge.models.Satellite;

import com.meli.challenge.models.exceptions.SatelliteException;

import java.util.List;

public interface ISatelliteService {

    List<Satellite> getAll() throws SatelliteException;

    Satellite getSatelliteByName(String satelliteName) throws SatelliteException;

    double[] getDistances() throws SatelliteException;

    double[] getDistances(List<Satellite> satellites) throws SatelliteException;

    List<List<String>> getMessages() throws SatelliteException;

    List<List<String>> getMessages(List<Satellite> satellites) throws SatelliteException;

    Satellite update(String satelliteName, Satellite satellite) throws SatelliteException;
}