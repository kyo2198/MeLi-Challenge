package com.meli.challenge.services;

import com.meli.challenge.models.Position;

import com.meli.challenge.models.Satellite;
import com.meli.challenge.models.exceptions.LocationException;
import com.meli.challenge.models.exceptions.SatelliteException;
import com.meli.challenge.models.services.ILocationService;

import com.meli.challenge.models.repositories.ISatelliteRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private ISatelliteRepository satelliteRepository;

    public LocationService() {
    }

    /**
     * <p>Este metodo permite determinar la posición de la nave enemiga a partir de
     * los datos interceptados por los satelites aliados.
     * </p>
     * @param distances array que contiene las distancias de cada uno de
     *                  los satelites aliados con respecto al enemigo.
     * @return Posicion final del enemigo.
     * @throws LocationException sera lanzada en el caso de que el array de distancias sea nulo
     * o que la informacion brindada provenga en un numero inferio o superio a 3 satelites.
     * @throws SatelliteException sera lanzada en el caso de que no se encuentren o ocurra un
     * un error al intentar obtenter los datos sobre los satelites aliados en la base de datos.
     * @author Mariano Olivera
     */
    public Position getLocation(double[] distances) throws SatelliteException, LocationException
    {
        if(distances == null)
        {
            throw new LocationException("Imposible continuar, no se ha suministrado información.");
        }

        if(distances.length < 3)
        {
            throw new LocationException("La información suministrada es insuficiente.");
        }

        if(distances.length > 3)
        {
            throw new LocationException("Se ha detectado información suministrada adicional, que proviene que una fuente desconocida.");
        }

        //Variables auxiliares
        double[] P1 = new double[2];
        double[] P2 = new double[2];
        double[] P3 = new double[2];

        double[] ex = new double[2];
        double[] ey = new double[2];

        double[] p3p1 = new double[2];

        double jval = 0;
        double temp = 0;
        double ival = 0;
        double p3p1i = 0;
        double triptx;
        double tripty;
        double xval;
        double yval;
        double t1;
        double t2;
        double t3;
        double t;
        double exx;

        double d;

        double eyy;

        try {
            //Obtengo las coordenas de posicion de los satelites
            Position[] satellitesPositions = getAllPositions();

            //Transformo las coordenadas en vectores
            //Punto 1
            P1[0] = satellitesPositions[0].getX();
            P1[1] = satellitesPositions[0].getY();

            //Punto 2
            P2[0] = satellitesPositions[1].getX();
            P2[1] = satellitesPositions[1].getY();

            //Punto 3
            P3[0] = satellitesPositions[2].getX();
            P3[1] = satellitesPositions[2].getY();

            for (int i = 0; i < P1.length; i++) {
                t1 = P2[i];
                t2 = P1[i];

                t = t1 - t2;

                temp += (t * t);
            }

            d = Math.sqrt(temp);

            for (int i = 0; i < P1.length; i++) {
                t1 = P2[i];
                t2 = P1[i];

                exx = ((t1 - t2) / (Math.sqrt(temp)));

                ex[i] = exx;
            }

            for (int i = 0; i < P3.length; i++) {
                t1 = P3[i];
                t2 = P1[i];

                t3 = t1 - t2;

                p3p1[i] = t3;
            }

            for (int i = 0; i < ex.length; i++) {
                t1 = ex[i];
                t2 = p3p1[i];

                ival += (t1 * t2);
            }

            for (int i = 0; i < P3.length; i++) {
                t1 = P3[i];
                t2 = P1[i];

                t3 = ex[i] * ival;

                t = t1 - t2 - t3;

                p3p1i += (t * t);
            }
            for (int i = 0; i < P3.length; i++) {
                t1 = P3[i];
                t2 = P1[i];

                t3 = ex[i] * ival;

                eyy = ((t1 - t2 - t3) / Math.sqrt(p3p1i));

                ey[i] = eyy;
            }

            for (int i = 0; i < ey.length; i++) {
                t1 = ey[i];

                t2 = p3p1[i];

                jval += (t1 * t2);
            }

            xval = ((Math.pow(distances[0], 2) - Math.pow(distances[1], 2) + Math.pow(d, 2)) / (2 * d));
            yval = (((Math.pow(distances[0], 2) - Math.pow(distances[2], 2) + Math.pow(ival, 2) + Math.pow(jval, 2)) / (2 * jval)) - ((ival / jval) * xval));

            t1 = satellitesPositions[0].getX();
            t2 = ex[0] * xval;
            t3 = ey[0] * yval;

            triptx = t1 + t2 + t3;

            t1 = satellitesPositions[0].getY();
            t2 = ex[1] * xval;
            t3 = ey[1] * yval;

            tripty = t1 + t2 + t3;
        }
        catch (SatelliteException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new LocationException("Ocurrio un error inesperado al intentar obtener la posicion del emisor.");
        }

        return new Position(roundTwoDecimals(triptx), roundTwoDecimals(tripty));
    }

    /**
     * <p>Este metodo permite obtener las posiciones de cada uno de los
     * satelites aliados.
     * </p>
     * @return Arrray con las posiciones de cada uno de los satelites aliados.
     * @throws SatelliteException sera lanzada en el caso de que no se encuentren o ocurra un
     * un error al intentar obtenter los datos sobre los satelites aliados en la base de datos.
     * @author Mariano Olivera
     */
    private Position[] getAllPositions() throws SatelliteException
    {
        Position positions[] = new Position[3];

        List<Satellite> satellites;

        try
        {
            satellites = satelliteRepository.findAll();
        }
        catch (Exception e)
        {
            throw new SatelliteException("Ocurrio un error al intentar obtener la informacion almacenada de los satellites.");
        }

        if(satellites == null)
        {
            throw new SatelliteException("No hay ninguna informacion almacenada de los satellites, por favor verifique.");
        }

        for(Satellite satellite : satellites)
        {
            positions[satellite.getIndex()] = satellite.getPosition();
        }

        return positions;
    }

    /**
     * <p>Metodo auxiliar, el cual se usa para poder redondedear a 2 decimales un
     * el valor pasado por parametro.
     * </p>
     * @param number valor recibido expresado en double.
     * @return Valor original expresado en double, redondeado a 2 decimales.
     * @author Mariano Olivera
     */
    private double roundTwoDecimals(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}