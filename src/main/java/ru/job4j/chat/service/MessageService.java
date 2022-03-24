package ru.job4j.chat.service;

import ru.job4j.chat.model.Message;

import java.util.Collection;
import java.util.Optional;

public interface MessageService {
    Collection<Message> findAll();

    Optional<Message> findById(int id);

    Message save(Message message);

    void delete(Message message);
}
