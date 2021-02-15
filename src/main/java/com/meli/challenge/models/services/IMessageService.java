package com.meli.challenge.models.services;

import com.meli.challenge.models.exceptions.MessageException;

import java.util.List;

public interface IMessageService {

    String getMessage(List<List<String>> partialMessage) throws MessageException;
}