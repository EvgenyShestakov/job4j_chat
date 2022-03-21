package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Message;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Override
    @EntityGraph(attributePaths = "person")
    Iterable<Message> findAll();

    @Override
    @EntityGraph(attributePaths = "person")
    Optional<Message> findById(Long id);
}
