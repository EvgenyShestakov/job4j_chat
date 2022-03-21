package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.chat.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    @Override
    @EntityGraph(attributePaths = "role")
    Iterable<Person> findAll();

    @Override
    @EntityGraph(attributePaths = "role")
    Optional<Person> findById(Long id);

    Person findPersonByUsername(String username);

}
