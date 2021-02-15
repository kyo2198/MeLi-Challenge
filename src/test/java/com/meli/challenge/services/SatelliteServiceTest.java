package com.meli.challenge.services;

import com.meli.challenge.models.Position;
import com.meli.challenge.models.Satellite;

import com.meli.challenge.models.exceptions.SatelliteException;

import com.meli.challenge.models.services.ISatelliteService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SatelliteServiceTest {

    @Autowired
    private ISatelliteService satelliteService;

    @Test
    public void getSatelliteByApprovedName() throws SatelliteException
    {
        Satellite satellite = satelliteService.getSatelliteByName("Kenobi");

        assertNotNull(satellite);
    }

    @Test
    public void getSatelliteByWrongName() throws SatelliteException
    {
        Satellite satellite = satelliteService.getSatelliteByName("Palpatine");

        assertNull(satellite);
    }

    @Test
    public void getDistancesFrom3Satellites() throws SatelliteException
    {
        List<Satellite> satellites = new ArrayList<>();
        satellites.add(new Satellite("Kenobi", 0, 100, new ArrayList<>(), new Position(0, 0)));
        satellites.add(new Satellite("Skywalker", 1, 350, new ArrayList<>(), new Position(0, 0)));
        satellites.add(new Satellite("Sato", 2, 980, new ArrayList<>(), new Position(0, 0)));

        double[] expectedDistances = new double[] { 100, 350, 980 };

        double[] resultDistances = satelliteService.getDistances(satellites);

        assertArrayEquals(expectedDistances, resultDistances);
    }

    @Test
    public void getDistancesFrom3SatellitesWithUnknownSource()
    {
        List<Satellite> satellites = new ArrayList<>();
        satellites.add(new Satellite("Kenobi", 0, 100, new ArrayList<>(), new Position(0, 0)));
        satellites.add(new Satellite("Skywalker", 1, 350, new ArrayList<>(), new Position(0, 0)));
        satellites.add(new Satellite("Yoda", 2, -150, new ArrayList<>(), new Position(0, 0)));

        Exception exception = assertThrows(SatelliteException.class, () ->
        {
            satelliteService.getDistances(satellites);
        });

        String expectedMessage = "Hay información de mensajes interceptados que provienen de una fuente no confirmada.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getDistancesFrom4Satellites()
    {
        List<Satellite> satellites = new ArrayList<>();
        satellites.add(new Satellite("Kenobi", 0, 100, new ArrayList<>(), new Position(0, 0)));
        satellites.add(new Satellite("Skywalker", 1, 350, new ArrayList<>(), new Position(0, 0)));
        satellites.add(new Satellite("Sato", 2, 980, new ArrayList<>(), new Position(0, 0)));
        satellites.add(new Satellite("C3PO", 3, -640, new ArrayList<>(), new Position(0, 0)));

        Exception exception = assertThrows(SatelliteException.class, () ->
        {
            satelliteService.getDistances(satellites);
        });

        String expectedMessage = "Hay información de mensajes interceptados que provienen de una fuente no confirmada.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getDistancesWhenNoSatellites()
    {
        Exception exception = assertThrows(SatelliteException.class, () ->
        {
            satelliteService.getDistances(null);
        });

        String expectedMessage = "No hay información de mensajes interceptados desde ningun satelite.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}