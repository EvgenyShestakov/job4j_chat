package ru.job4j.chat.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.PersonService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/person")
public class PersonController {
    private static final String API_MESSAGE = "http://localhost:8080/message/";

    private static final String API_ID_MESSAGE = "http://localhost:8080/message/{id}";

    private static final String API_ROOM = "http://localhost:8080/room/";

    private static final String API_ID_ROOM = "http://localhost:8080/room/{id}";

    private final PersonService service;

    private final RestTemplate rest;

    public PersonController(PersonService service, RestTemplate rest) {
        this.service = service;
        this.rest = rest;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return StreamSupport.stream(
                this.service.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = service.findById(id);
        return new ResponseEntity<>(person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(service.save(person), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        service.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        this.service.delete(person);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/room")
    public List<Room> findAllRooms() {
        return rest.exchange(
                API_ROOM,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Room>>() { }
        ).getBody();
    }

    @GetMapping("/room/{id}")
    public Room findRoomById(@PathVariable int id) {
       return rest.getForObject(API_ID_ROOM, Room.class, id);
    }

    @PostMapping("/room")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room rsl = rest.postForObject(API_ROOM, room, Room.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/room")
    public ResponseEntity<Void> updateRoom(@RequestBody Room room) {
        rest.put(API_ROOM, room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        rest.delete(API_ID_ROOM, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/message")
    public List<Message> findAllMessages() {
        return rest.exchange(
                API_MESSAGE,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Message>>() { }
        ).getBody();
    }

    @GetMapping("/message/{id}")
    public Message findMessageById(@PathVariable int id) {
        return rest.getForObject(API_ID_MESSAGE, Message.class, id);
    }

    @PostMapping("/message")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message rsl = rest.postForObject(API_MESSAGE, message, Message.class);
        return new ResponseEntity<>(rsl, HttpStatus.CREATED);
    }

    @PutMapping("/message")
    public ResponseEntity<Void> updateMessage(@RequestBody Message message) {
        rest.put(API_MESSAGE, message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/message/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int id) {
        rest.delete(API_ID_MESSAGE, id);
        return ResponseEntity.ok().build();
    }
}
