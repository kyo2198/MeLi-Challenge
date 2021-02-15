package com.meli.challenge.services;

import com.meli.challenge.models.Position;

import com.meli.challenge.models.exceptions.LocationException;
import com.meli.challenge.models.exceptions.SatelliteException;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void getLocationFrom3Satellites() throws SatelliteException, LocationException
    {
        double[] distances = new double[] { 100, 350, 950};

        Position expectedPosition = new Position(-140.62, -1068.75);

        Position resultPosition = locationService.getLocation(distances);

        assertEquals(expectedPosition, resultPosition);
    }

    @Test
    public void getLocationWhenNoSatellites()
    {
        Exception exception = assertThrows(LocationException.class, () ->
        {
            locationService.getLocation(null);
        });

        String expectedMessage = "Imposible continuar, no se ha suministrado información.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getLocationFrom2Satellites()
    {
        double[] distances = new double[] { -150, 210};

        Exception exception = assertThrows(LocationException.class, () ->
        {
            locationService.getLocation(distances);
        });

        String expectedMessage = "La información suministrada es insuficiente.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getLocationFrom4Satellites()
    {
        double[] distances = new double[] { -10, 410, -620, 1500};

        Exception exception = assertThrows(LocationException.class, () ->
        {
            locationService.getLocation(distances);
        });

        String expectedMessage = "Se ha detectado información suministrada adicional, que proviene que una fuente desconocida.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}