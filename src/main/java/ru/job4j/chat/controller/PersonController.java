package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.dto.PersonDTO;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoleService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    private final RoleService roleService;

    private final ObjectMapper objectMapper;

    private final BCryptPasswordEncoder encoder;

    public PersonController(PersonService personService, RoleService roleService,
                            ObjectMapper objectMapper, BCryptPasswordEncoder encoder) {
        this.personService = personService;
        this.roleService = roleService;
        this.objectMapper = objectMapper;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return StreamSupport.stream(
                this.personService.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Person person = personService.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Person is not found."));
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        isNull(person);
        if (person.getEmail().endsWith("@gmail.com")) {
            throw new IllegalArgumentException("Invalid mailing address");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(personService.save(person), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        isNull(person);
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        this.personService.delete(person);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Person> patch(@RequestBody PersonDTO personDTO) {
        if (personDTO.getUsername() == null || personDTO.getPassword() == null
                || personDTO.getEmail() == null) {
            throw new NullPointerException("Fields must not be empty");
        }
        Role role = roleService.findById(personDTO.getRoleID())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Person person = new Person(personDTO.getId(), personDTO.getUsername(),
                personDTO.getPassword(), personDTO.getEmail(), role);
        return new ResponseEntity<>(personService.save(person), HttpStatus.OK);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void handleException(Exception e, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Error");
            put("details", e.getMessage());
        }}));
    }

    private void isNull(Person person) {
        String username = person.getUsername();
        String password = person.getPassword();
        String email = person.getEmail();
        Role role = person.getRole();
        if (username == null || password == null || email == null || role == null) {
            throw new NullPointerException("Username, password, email and role mustn't be empty");
        }
    }
}
