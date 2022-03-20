package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Message;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Override
    @EntityGraph(attributePaths = {"person", "room"})
    Iterable<Message> findAll();

    @Override
    @EntityGraph(attributePaths = {"person", "room"})
    Optional<Message> findById(Long id);
}
