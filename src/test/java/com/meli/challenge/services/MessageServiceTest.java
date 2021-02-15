package com.meli.challenge.services;

import com.meli.challenge.models.exceptions.MessageException;

import com.meli.challenge.models.services.IMessageService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private IMessageService messageService;

    @Test
    public void getMessageFrom3SatellitesWithSameSize() throws MessageException
    {
        List<List<String>> messages = new ArrayList<List<String>>();
        messages.add(new ArrayList() {{ add("Este"); add(""); add(""); add("mensaje"); add(""); }});
        messages.add(new ArrayList() {{ add(""); add("es"); add(""); add(""); add("secreto"); }});
        messages.add(new ArrayList() {{ add("Este"); add(""); add("un"); add(""); add(""); }});

        String expectedMessage = "Este es un mensaje secreto";

        String resultMessage = messageService.getMessage(messages);

        assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void getMessageFrom3SatellitesWithDifferentSize() throws MessageException
    {
        List<List<String>> messages = new ArrayList<List<String>>();
        messages.add(new ArrayList() {{ add("Este"); add(""); add(""); add("mensaje"); add(""); }});
        messages.add(new ArrayList() {{ add(""); add(""); add("es"); add("un"); add(""); add(""); }});
        messages.add(new ArrayList() {{ add(""); add(""); add(""); add("es"); add("un"); add(""); add("secreto"); }});

        String expectedMessage = "Este es un mensaje secreto";

        String resultMessage = messageService.getMessage(messages);

        assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void getMessageFrom2Satellites()
    {
        List<List<String>> messages = new ArrayList<List<String>>();
        messages.add(new ArrayList() {{ add("Este"); add(""); add(""); add("mensaje"); add(""); }});
        messages.add(new ArrayList() {{ add(""); add("es"); add(""); add(""); add("secreto"); }});

        Exception exception = assertThrows(MessageException.class, () ->
        {
            messageService.getMessage(messages);
        });

        String expectedMessage = "Para poder decodificar el mensaje, es necesario que los 3 satelites envien sus mensajes parciales.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getMessageFrom4Satellites()
    {
        List<List<String>> messages = new ArrayList<List<String>>();
        messages.add(new ArrayList() {{ add("Este"); add(""); add(""); add("mensaje"); add(""); }});
        messages.add(new ArrayList() {{ add(""); add("es"); add(""); add(""); add("secreto"); }});
        messages.add(new ArrayList() {{ add("Este"); add(""); add("un"); add(""); add(""); }});
        messages.add(new ArrayList() {{ add(""); add(""); add("un"); add(""); add("secreto"); }});

        Exception exception = assertThrows(MessageException.class, () ->
        {
            messageService.getMessage(messages);
        });

        String expectedMessage = "El mensaje no puede ser decodificado correctamente dado que se ha recibido informacion adicional que proviene que una fuente desconocida.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getMessageFrom3SatellitesWithInsufficientInformation()
    {
        List<List<String>> messages = new ArrayList<List<String>>();
        messages.add(new ArrayList() {{ add("Este"); add(""); add(""); add("mensaje"); add(""); add(""); }});
        messages.add(new ArrayList() {{ add(""); add("es"); add(""); add(""); add(""); }});
        messages.add(new ArrayList() {{ add("Este"); add(""); add("un"); add(""); add(""); }});

        Exception exception = assertThrows(MessageException.class, () ->
        {
            messageService.getMessage(messages);
        });

        String expectedMessage = "No hay información suciente para decodificar el mensaje interceptado.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getMessageFrom3SatellitesWithNoInformation()
    {
        List<List<String>> messages = new ArrayList<List<String>>();
        messages.add(new ArrayList());
        messages.add(new ArrayList());
        messages.add(new ArrayList());

        Exception exception = assertThrows(MessageException.class, () ->
        {
            messageService.getMessage(messages);
        });

        String expectedMessage = "La información recibida desde los 3 satellites esta en blanco.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getMessageFrom3SatellitesWithEmptyInformation()
    {
        List<List<String>> messages = new ArrayList<List<String>>();
        messages.add(new ArrayList() {{ add(""); }});
        messages.add(new ArrayList() {{ add(""); }});
        messages.add(new ArrayList() {{ add(""); }});

        Exception exception = assertThrows(MessageException.class, () ->
        {
            messageService.getMessage(messages);
        });

        String expectedMessage = "La información recibida desde los 3 satellites esta en blanco.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getMessageFrom3SatellitesWithNullInformationV1()
    {
        Exception exception = assertThrows(MessageException.class, () ->
        {
            messageService.getMessage(null);
        });

        String expectedMessage = "La información recibida es nula.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getMessageFrom3SatellitesWithNullInformationV2()
    {
        List<List<String>> messages = new ArrayList<List<String>>();
        messages.add(new ArrayList() {{ add("Este"); add(""); add(""); add("mensaje"); add(""); }});
        messages.add(null);
        messages.add(new ArrayList() {{ add("Este"); add(""); add("un"); add(""); add(""); }});

        Exception exception = assertThrows(MessageException.class, () ->
        {
            messageService.getMessage(messages);
        });

        String expectedMessage = "La información recibida desde alguno de los 3 satellites es nula.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}