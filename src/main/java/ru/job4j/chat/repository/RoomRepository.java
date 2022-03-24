package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Room;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    @Override
    @EntityGraph(attributePaths = {"messages", "people"})
    Iterable<Room> findAll();

    @Override
    @EntityGraph(attributePaths = {"messages", "people"})
    Optional<Room> findById(Integer id);

}
