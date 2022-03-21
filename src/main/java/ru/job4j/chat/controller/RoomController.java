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
import ru.job4j.chat.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/room")
public class RoomController {
    private static final String API_MESSAGE = "http://localhost:8080/message/";

    private static final String API_ID_MESSAGE = "http://localhost:8080/message/{id}";

    private static final String API_PERSON = "http://localhost:8080/person/";

    private static final String API_ID_PERSON = "http://localhost:8080/person/{id}";

    private final RoomService service;

    private final RestTemplate rest;

    public RoomController(RoomService service, RestTemplate rest) {
        this.service = service;
        this.rest = rest;
    }

    @GetMapping("/")
    public List<Room> findAll() {
        return StreamSupport.stream(
                this.service.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        var person = service.findById(id);
        return new ResponseEntity<>(person.orElse(new Room()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return new ResponseEntity<>(service.save(room), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        service.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = new Room();
       room.setId(id);
        this.service.delete(room);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/person")
    public List<Person> findAllRooms() {
        return rest.exchange(
                API_PERSON,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() { }
        ).getBody();
    }

    @GetMapping("/person/{id}")
    public Person findRoomById(@PathVariable int id) {
        return rest.getForObject(API_ID_PERSON, Person.class, id);
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createRoom(@RequestBody Person person) {
        Person rsl = rest.postForObject(API_PERSON, person, Person.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/person")
    public ResponseEntity<Void> updateRoom(@RequestBody Person person) {
        rest.put(API_PERSON, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        rest.delete(API_ID_PERSON, id);
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
