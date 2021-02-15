package com.meli.challenge.services;

import com.meli.challenge.models.services.IMessageService;
import com.meli.challenge.models.exceptions.MessageException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class MessageService implements IMessageService {

    public MessageService() {
    }

    /**
     * <p>Este metodo permite obtener y descifrar de ser posible, el mensaje
     * interceptado por los satelites aliados.
     * </p>
     * @param messages lista con los mensajes interceptados por cada uno de los
     *                 3 satelites aliados.
     * @return Mensaje decodificado emitido por el enemigo.
     * @throws MessageException sera lanzada en el caso de que la lista de mensajes sea nula
     * o que la informacion brindada provenga en un numero inferior o superior a 3 satelites.
     * @author Mariano Olivera
     */
    public String getMessage(List<List<String>> messages) throws MessageException
    {
        if(messages == null)
        {
            throw new MessageException("La información recibida es nula.");
        }

        if (messages.size() < 3)
        {
            throw new MessageException("Para poder decodificar el mensaje, es necesario que los 3 satelites envien sus mensajes parciales.");
        }

        if (messages.size() > 3)
        {
            throw new MessageException("El mensaje no puede ser decodificado correctamente dado que se ha recibido informacion adicional que proviene que una fuente desconocida.");
        }

        messages = removeLag(messages);

        return getCompleteMessage(messages);
    }

    /**
     * <p>Este metodo se encarga de determinar y remover el posible desafasaje en los mensajes
     * interceptados.
     * </p>
     * @param messages lista con los mensajes interceptados por cada uno de los
     *                 3 satelites aliados.
     * @return Lista con los mensajes interceptados por cada uno de los 3 satelites sin desfasaje.
     * @throws MessageException sera lanzada en el caso de que la lista de mensajes contenga
     * informacion nula proveniente de alguno de los satelites aliados o en el caso de que no se
     * haya podido determinar el mensaje del enemigo.
     * @author Mariano Olivera
     */
    private List<List<String>> removeLag(List<List<String>> messages) throws MessageException
    {
        CheckNullInformation(messages);

        List<Integer> sizes =  new ArrayList<>();
        sizes.add(messages.get(0).size());
        sizes.add(messages.get(1).size());
        sizes.add(messages.get(2).size());

        CheckEmptyInformation(messages);

        int maxSize = 0;
        int minSize = Integer.MAX_VALUE;

        //Obtengo el valor maximo
        for(Integer size : sizes) {
            if(size > maxSize)
                maxSize = size;
        }

        //Obtengo el valor minino
        for(Integer size : sizes) {
            if(size < minSize)
                minSize = size;
        }

        if(maxSize != minSize)
        {
            for(List<String> message: messages)
            {
                while(message.size() > minSize)
                {
                    if(message.get(0).trim().equals("") == false)
                    {
                        throw new MessageException("No hay información suciente para decodificar el mensaje interceptado.");
                    }

                    message.remove(0);
                }
            }
        }
        else
        {
            if(maxSize == 0 && minSize == 0)
            {
                throw new MessageException("No hay información suciente para decodificar el mensaje interceptado.");
            }
        }

        for(List<String> message: messages)
        {
            if(message.size() != minSize)
            {
                throw new MessageException("No hay información suciente para decodificar el mensaje interceptado.");
            }
        }

        return messages;
    }

    /**
     * <p>Este metodo se encarga de determinar si todos los satelites pudieron inteceptar
     * satisfactoriamente el mensaje del enemigo.
     * </p>
     * @param messages lista con los mensajes interceptados por cada uno de los
     *                 3 satelites aliados.
     * @throws MessageException sera lanzada en el caso de que la lista de mensajes contenga
     * informacion nula proveniente de alguno de los satelites aliados.
     * @author Mariano Olivera
     */
    private void CheckNullInformation(List<List<String>> messages) throws MessageException
    {
        for(List<String> message : messages)
        {
            if(message == null)
            {
                throw new MessageException("La información recibida desde alguno de los 3 satellites es nula.");
            }
        }
    }

    /**
     * <p>Este metodo se encarga de determinar y remover el posible desafasaje en los mensajes
     * interceptados.
     * </p>
     * @param messages lista con los mensajes interceptados por cada uno de los
     *                 3 satelites aliados.
     * @throws MessageException sera lanzada en el caso de que la lista de mensajes contenga
     * informacion vacia proveniente de los 3 satelites aliados.
     * @author Mariano Olivera
     */
    private void CheckEmptyInformation(List<List<String>> messages) throws MessageException
    {
        String allWords = "";

        for(List<String> message : messages)
        {
            for(String messageItem : message)
            {
                allWords += messageItem;
            }
        }

        if(allWords.trim().isEmpty() || allWords.trim().isBlank())
        {
            throw new MessageException("La información recibida desde los 3 satellites esta en blanco.");
        }
    }

    /**
     * <p>Este metodo es el que se encarga, una vez pasados todos los controles de armar y
     * decodificar el mensaje del enemigo.
     * </p>
     * @param messages lista con los mensajes interceptados por cada uno de los
     *                 3 satelites aliados.
     * @return Mensaje decodificado emitido por el enemigo.
     * @author Mariano Olivera
     */
    private String getCompleteMessage(List<List<String>> messages)
    {
        String[] completeMessageList = new String[messages.get(0).size()];

        for(List<String> message: messages)
        {
            for(int i = 0; i < message.size(); i++)
            {
                if(message.get(i).trim().equals("") == false)
                {
                    completeMessageList[i] = message.get(i);
                }
            }
        }

        String completeMessage = "";

        for(String completeMessageItem : completeMessageList)
        {
            completeMessage += completeMessageItem + " ";
        }

        return completeMessage.trim();
    }
}