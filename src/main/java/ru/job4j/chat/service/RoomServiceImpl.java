package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRep;

    public RoomServiceImpl(RoomRepository roomRep) {
        this.roomRep = roomRep;
    }

    @Override
    public Collection<Room> findAll() {
        List<Room> res = new ArrayList<>();
        roomRep.findAll().forEach(res::add);
        return res;
    }

    @Override
    public Optional<Room> findById(long id) {
        return roomRep.findById(id);
    }

    @Override
    public Room save(Room room) {
        return roomRep.save(room);
    }

    @Override
    public void delete(Room room) {
    roomRep.delete(room);
    }
}
