package ru.job4j.chat.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.job4j.chat.model.Person;

import java.util.Collection;
import java.util.Optional;

public interface PersonService extends UserDetailsService {
    Collection<Person> findAll();

    Optional<Person> findById(int id);

    Person save(Person person);

    void delete(Person person);

    Person findPersonByUsername(String name);
}
