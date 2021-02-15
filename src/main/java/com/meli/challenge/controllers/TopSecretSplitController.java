package com.meli.challenge.controllers;

import com.meli.challenge.models.ErrorResponse;
import com.meli.challenge.models.Satellite;

import com.meli.challenge.models.TopSecretSplitResponse;

import com.meli.challenge.models.exceptions.MessageException;
import com.meli.challenge.models.exceptions.LocationException;
import com.meli.challenge.models.exceptions.SatelliteException;

import com.meli.challenge.models.services.IMessageService;
import com.meli.challenge.models.services.ILocationService;
import com.meli.challenge.models.services.ISatelliteService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
public class TopSecretSplitController {

    @Autowired
    private ILocationService locationService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private ISatelliteService satelliteService;

    @PostMapping
    @RequestMapping(path = "/topsecret_split/{satellite_name}")
    public ResponseEntity topSecretSplitPost(@PathVariable("satellite_name") String satelliteName, @RequestBody Satellite satellite) {

        try
        {
            Satellite updatedSatellite = satelliteService.update(satelliteName, satellite);

            return ResponseEntity.status(HttpStatus.OK).body(updatedSatellite);
        }
        catch (SatelliteException e)
        {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping
    @RequestMapping(path = "/topsecret_split/")
    public ResponseEntity topSecretSplitGet() {

        try
        {
            TopSecretSplitResponse topSecretSplitResponse = new TopSecretSplitResponse();
            topSecretSplitResponse.setPosition(locationService.getLocation(satelliteService.getDistances()));
            topSecretSplitResponse.setMessage(messageService.getMessage(satelliteService.getMessages()));

            return ResponseEntity.status(HttpStatus.OK).body(topSecretSplitResponse);
        }
        catch (SatelliteException e)
        {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        catch (LocationException e)
        {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        catch (MessageException e)
        {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}