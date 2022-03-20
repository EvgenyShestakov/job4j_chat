package ru.job4j.chat.service;

import ru.job4j.chat.model.Person;

import java.util.Collection;
import java.util.Optional;

public interface PersonService {
    Collection<Person> findAll();

    Optional<Person> findById(long id);

    Person save(Person person);

    void delete(Person person);
}
