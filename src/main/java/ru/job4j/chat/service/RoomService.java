package ru.job4j.chat.service;

import ru.job4j.chat.model.Room;

import java.util.Collection;
import java.util.Optional;

public interface RoomService {
    Collection<Room> findAll();

    Optional<Room> findById(long id);

    Room save(Room room);

    void delete(Room room);
}
