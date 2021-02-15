package com.meli.challenge.models;

import java.util.List;

public class TopSecretRequest {

    private List<Satellite> satellites;

    public void setSatellites(List<Satellite> satellites)
    {
        this.satellites = satellites;
    }

    public List<Satellite> getSatellites()
    {
        return satellites;
    }
}