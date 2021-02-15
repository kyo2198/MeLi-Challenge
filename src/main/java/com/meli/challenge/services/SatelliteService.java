package com.meli.challenge.services;

import com.meli.challenge.models.Position;
import com.meli.challenge.models.Satellite;

import com.meli.challenge.models.repositories.ISatelliteRepository;

import com.meli.challenge.models.services.ISatelliteService;

import com.meli.challenge.models.exceptions.SatelliteException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

@Service
public class SatelliteService implements ISatelliteService {

    @Autowired
    private ISatelliteRepository satelliteRepository;

    public SatelliteService() {
    }

    /**
     * <p>Este metodo permite obtener todos los satelites aliados almacenados en la
     * base de datos.
     * </p>
     * @return Lista con todos los satelites aliados.
     * @throws SatelliteException sera lanzada en el caso de que ocurra algun problema
     * al intentar obtener todos los satelites aliados.
     * @author Mariano Olivera
     */
    public List<Satellite> getAll() throws SatelliteException
    {
        List<Satellite> satellites;

        try
        {
            satellites = satelliteRepository.findAll();
        }
        catch(Exception e)
        {
            throw new SatelliteException("Ocurrio un error al intentar obtener la informacion almacenada de los satellites.");
        }

        return satellites;
    }

    /**
     * <p>Este metodo permite obtener la información almacenada sobre el satelite aliado
     * que se ha pasado como parametro.
     * </p>
     * @param satelliteName nombre del satelite aliado el cual se debera obtener.
     * @return De existir el nombre, retornara la información del satelite aliado.
     * @throws SatelliteException sera lanzada en el caso de que ocurra algun problema
     * al intentar la información del satelite aliado.
     * @author Mariano Olivera
     */
    public Satellite getSatelliteByName(String satelliteName) throws SatelliteException
    {
        Satellite savedSatellite;

        try
        {
            savedSatellite = satelliteRepository.findByName(satelliteName.toLowerCase());
        }
        catch (Exception e)
        {
            throw new SatelliteException("Ocurrio un error al intentar obtener la informacion almacenada del satellite " + satelliteName);
        }

        return savedSatellite;
    }

    /**
     * <p>Este metodo permite obtener las distancias al enemigo de cada uno de los
     * satelites aliados.
     * </p>
     * @return Lista con las distancias al enemigo de cada uno de los satelites aliados.
     * @throws SatelliteException sera lanzada en el caso de que ocurra algun problema
     * al intentar obtener información sobre los satelites aliados.
     * @author Mariano Olivera
     */
    public double[] getDistances() throws SatelliteException
    {
        List<Satellite> satellites = getAll();

        return getDistances(satellites);
    }

    /**
     * <p>Este metodo permite obtener las distancias al enemigo de cada uno de los
     * satelites aliados.
     * </p>
     * @param satellites lista con la información de cada uno de los satelites aliados.
     * @return Lista con las distancias al enemigo de cada uno de los satelites aliados.
     * @throws SatelliteException sera lanzada en el caso de que la lista de satelites
     * aliados sea nula, que la cantidad de satelites sea inferior 3 o si en la lista
     * hay información de un satelite no perteneciente a la lista de los aliados.
     * @author Mariano Olivera
     */
    public double[] getDistances(List<Satellite> satellites) throws SatelliteException
    {
        CheckAllSatellites(satellites);

        double [] distances = new double[satellites.size()];

        for(Satellite satellite : satellites) {
            if(satellite.getName().toLowerCase().equals("kenobi"))
                distances[0] = satellite.getDistance();

            if(satellite.getName().toLowerCase().equals("skywalker"))
                distances[1] = satellite.getDistance();

            if(satellite.getName().toLowerCase().equals("sato"))
                distances[2] = satellite.getDistance();
        }

        return distances;
    }

    /**
     * <p>Este metodo permite obtener la lista de mensajes interceptados por cada uno
     * de los satelites aliados.
     * </p>
     * @return Lista con los mensajes interceptados por cada uno de los satelites aliados.
     * @throws SatelliteException sera lanzada en el caso de que la lista de satelites
     * aliados sea nula, que la cantidad de satelites sea inferior 3 o si en la lista
     * hay información de un satelite no perteneciente a la lista de los aliados.
     * @author Mariano Olivera
     */
    public List<List<String>> getMessages() throws SatelliteException
    {
        List<Satellite> satellites = getAll();

        List<List<String>> messages = getMessages(satellites);

        return messages;
    }

    /**
     * <p>Este metodo permite obtener la lista de mensajes interceptados por cada uno
     * de los satelites aliados.
     * </p>
     * @param satellites lista con la información de cada uno de los satelites aliados.
     * @return Lista con los mensajes interceptados por cada uno de los satelites aliados.
     * @throws SatelliteException sera lanzada en el caso de que la lista de satelites
     * aliados sea nula, que la cantidad de satelites sea inferior 3 o si en la lista
     * hay información de un satelite no perteneciente a la lista de los aliados.
     * @author Mariano Olivera
     */
    public List<List<String>> getMessages(List<Satellite> satellites) throws SatelliteException
    {
        CheckAllSatellites(satellites);

        List<List<String>> messages = new ArrayList<List<String>>();

        for(Satellite satellite : satellites){
            messages.add(satellite.getMessage());
        }

        return messages;
    }

    /**
     * <p>Este metodo permite actualizar la informacón almacenada de los satelites
     * en la base de datos.
     * </p>
     * @param satelliteName nombre del satelite a ser actualizado.
     * @param satellite nueva información para actualizar.
     * @return El satelite actualizado con su nueva información.
     * @throws SatelliteException sera lanzada en el caso de que ocurra algun problema
     * al actualizar los datos almacenados.
     * @author Mariano Olivera
     */
    public Satellite update(String satelliteName, Satellite satellite) throws SatelliteException
    {
        Satellite savedSatellite = getSatelliteByName(satelliteName.toLowerCase());

        if(savedSatellite == null)
        {
            savedSatellite = new Satellite();
            savedSatellite.setName(satelliteName);
            savedSatellite.setPosition(new Position(0, 0));
        }

        savedSatellite.setDistance(satellite.getDistance());
        savedSatellite.setMessage(satellite.getMessage());

        try
        {
            satelliteRepository.save(savedSatellite);
        }
        catch (Exception e)
        {
            throw new SatelliteException("Ocurrio un error al intentar guardar la informacion interceptada.");
        }

        return savedSatellite;
    }

    /**
     * <p>Este metodo se encarga de determinar que la lista de satelites no sea nula, no
     * contenga información proveniente de satelites no aliados y de que la información
     * provenga de los 3 satelites aliados.
     * </p>
     * @param satellites lista con la información de cada uno de los satelites aliados.
     * @throws SatelliteException sera lanzada en el caso de que la lista de satelites
     * aliados sea nula, que la cantidad de satelites sea inferior 3 o si en la lista
     * hay información de un satelite no perteneciente a la lista de los aliados.
     * @author Mariano Olivera
     */
    private void CheckAllSatellites(List<Satellite> satellites) throws SatelliteException
    {
        List<String> validNames = new ArrayList<>();
        validNames.add("kenobi");
        validNames.add("skywalker");
        validNames.add("sato");

        if(satellites == null)
        {
            throw new SatelliteException("No hay información de mensajes interceptados desde ningun satelite.");
        }

        if(satellites.size() < 3)
        {
            throw new SatelliteException("La información de mensajes interceptados es muy poca, aun falta que algunos satelites aporten sus datos.");
        }

        for(Satellite satellite : satellites)
        {
            if(validNames.contains(satellite.getName().toLowerCase()) == false)
            {
                throw new SatelliteException("Hay información de mensajes interceptados que provienen de una fuente no confirmada.");
            }
        }
    }
}