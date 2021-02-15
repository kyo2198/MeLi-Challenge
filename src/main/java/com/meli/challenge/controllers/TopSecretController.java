package com.meli.challenge.controllers;

import com.meli.challenge.models.ErrorResponse;
import com.meli.challenge.models.TopSecretRequest;
import com.meli.challenge.models.TopSecretResponse;

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
public class TopSecretController {

    @Autowired
    private ISatelliteService satelliteService;

    @Autowired
    private ILocationService locationService;

    @Autowired
    private IMessageService messageService;

    @PostMapping
    @RequestMapping(path = "/topsecret/")
    public ResponseEntity topSecret(@RequestBody TopSecretRequest topSecretRequest)
    {

        try
        {
            TopSecretResponse topSecretResponse = new TopSecretResponse();
            topSecretResponse.setPosition(locationService.getLocation(satelliteService.getDistances(topSecretRequest.getSatellites())));
            topSecretResponse.setMessage(messageService.getMessage(satelliteService.getMessages(topSecretRequest.getSatellites())));

            return ResponseEntity.status(HttpStatus.OK).body(topSecretResponse);
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