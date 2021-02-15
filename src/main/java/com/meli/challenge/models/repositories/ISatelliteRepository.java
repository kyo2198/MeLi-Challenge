package com.meli.challenge.models.repositories;

import com.meli.challenge.models.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISatelliteRepository extends JpaRepository<Satellite, String>
{
    Satellite findByName(String satelliteName);
}