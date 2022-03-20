package ru.job4j.chat.service;

import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;

import java.util.Collection;
import java.util.Optional;

public interface MessageService {
    Collection<Message> findAll();

    Optional<Message> findById(long id);

    Message save(Message message);

    void delete(Message message);
}
