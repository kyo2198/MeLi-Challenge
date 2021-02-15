# MeLi-Challenge
Mercado Libre Challenge: Operación fuego de Quasar

## Arquitectura
La arquitectura utilizada para el desarrollo de este proyecto fue realizada Java Spring Boot con Maven para resolver las dependencias, Java Target Version 11, y H2 para la base de datos.

## Paquetes

Se utiliza una distribución en capas para separar las responsabilidades, quedando de esta manera:

- Controllers
- Services
- Repositories
- Models

Dentro del paquete Models se puede apreciar tambien la siguiente distribución
- Exceptions
- Repositoies (Interfaces)
- Services (Interfaces)

## Despliege

El despliegue de la API se realizo sobre Google Cloud App Engine.

## Ejecución

Se puede descargar el proyecto GitHub y ejecutar por medio de Spring Boot el cual sera desplegado en: http://localhost:8080/

### Endpoints
Locales:

[POST] http://localhost:8080/topsecret/
Ejemplo de body:
{
    "satellites": [
        {
            "name": "kenobi",
            "distance": 100,
            "message": ["", "este", "", "", "mensaje", ""]
        },
        {
            "name": "skywalker",
            "distance": 350,
            "message": ["", "es", "", "", "secreto"]
        },
        {
            "name": "sato",
            "distance": 950,
            "message": ["", "", "este", "", "un", "", ""]
        }
    ]
}

[POST] http://localhost:8080/topsecret_split/{satellite_name}
Ejemplo de body:
{
    "distance": 350.0,
    "message": ["", "", "", "es", "un", "", "secreto"]
}

[GET] http://localhost:8080/topsecret_split/


Endpoints alojados en la nube:

[POST] https://meli-challenge-304806.rj.r.appspot.com/topsecret/

[POST] https://meli-challenge-304806.rj.r.appspot.com/{satellite_name}

[GET] https://meli-challenge-304806.rj.r.appspot.com/topsecret_split/
