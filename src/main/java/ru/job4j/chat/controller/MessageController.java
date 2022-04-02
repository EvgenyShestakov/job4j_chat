package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.dto.MessageDTO;
import ru.job4j.chat.exeption.Operation;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.PersonService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Validated
@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;
    private final PersonService personService;

    public MessageController(MessageService messageService, PersonService personService) {
        this.messageService = messageService;
        this.personService = personService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Message>> findAll() {
        List<Message> messages = StreamSupport.stream(
                this.messageService.findAll().spliterator(), false
        ).collect(Collectors.toList());
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        Message message = messageService.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Message is not found."));
    return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Message> create(@Valid @RequestBody Message message) {
        if (message.getBody() == null) {
            throw new NullPointerException("Message body must not be empty");
        }
        return new ResponseEntity<>(messageService.save(message), HttpStatus.CREATED);
    }

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Message message) {
        if (message.getBody() == null) {
            throw new NullPointerException("Message body must not be empty");
        }
        messageService.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Message message = new Message();
        message.setId(id);
        this.messageService.delete(message);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Message> patch(@Valid @RequestBody MessageDTO messageDTO) {
        if (messageDTO.getBody() == null) {
            throw new NullPointerException("Message body must not be empty");
        }
        Person person = personService.findById(messageDTO.getPersonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Message message = new Message(messageDTO.getId(), messageDTO.getBody(), person);
        return new ResponseEntity<>(messageService.save(message), HttpStatus.OK);
    }
}
