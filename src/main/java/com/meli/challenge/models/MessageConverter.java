package com.meli.challenge.models;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.AttributeConverter;

public class MessageConverter implements AttributeConverter<List<String>, String> {

    private static final String SEPARATOR = "#SEPARATOR#";

    @Override
    public String convertToDatabaseColumn(List<String> message) {
        StringBuilder stringBuilder = new StringBuilder();

        for(String messageItem : message)
        {
            if(messageItem.trim().isBlank() || messageItem.trim().isEmpty())
            {
                stringBuilder.append("#_BLANK_#");
            }
            else
            {
                stringBuilder.append(messageItem);
            }

            stringBuilder.append(SEPARATOR);
        }

        return stringBuilder.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String messageString) {
        String[] messageStringSplited = messageString.split(SEPARATOR);

        List<String> message = new ArrayList<>();

        for(String messageStringSplitedItem : messageStringSplited)
        {
            if(messageStringSplitedItem.equals("#_BLANK_#"))
            {
                message.add("");
            }
            else
            {
                message.add(messageStringSplitedItem);
            }
        }

        return message;
    }
}