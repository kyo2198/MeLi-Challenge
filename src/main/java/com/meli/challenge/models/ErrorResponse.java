package com.meli.challenge.models;

/**
 * Esta clase encapsula los mensajes de error lanzados por excepciones
 * @author Mariano Olivera
 */
public class ErrorResponse {

    /**
     * Codigo del error devuelto
     */
    private String status;

    /**
     * Mensaje lanzado por la excepción
     */
    private String message;

    public ErrorResponse() { }

    public ErrorResponse(String status, String message)
    {
        this.status = status;
        this.message = message;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}