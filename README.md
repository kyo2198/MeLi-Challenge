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

[POST] http://localhost:8080/topsecret/<br/>
Ejemplo de body:<br/>
{<br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"satellites": [<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "kenobi",<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"distance": 100,<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"message": ["", "este", "", "", "mensaje", ""]<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "skywalker",<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"distance": 350,<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"message": ["", "es", "", "", "secreto"]<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "sato",<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"distance": 950,<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"message": ["", "", "este", "", "un", "", ""]<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]<br/>
}<br/>

[POST] http://localhost:8080/topsecret_split/{satellite_name}<br/>
Ejemplo de body:<br/>
{<br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"distance": 350.0,<br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"message": ["", "", "", "es", "un", "", "secreto"]<br/>
}<br/>

[GET] http://localhost:8080/topsecret_split/

<br/>
Endpoints alojados en la nube:<br/><br/>
[POST] https://meli-challenge-304806.rj.r.appspot.com/topsecret/<br/><br/>
[POST] https://meli-challenge-304806.rj.r.appspot.com/{satellite_name}<br/><br/>
[GET] https://meli-challenge-304806.rj.r.appspot.com/topsecret_split/<br/><br/>
