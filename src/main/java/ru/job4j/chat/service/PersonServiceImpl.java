package ru.job4j.chat.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRep;

    public PersonServiceImpl(PersonRepository personRep) {
        this.personRep = personRep;
    }

    @Override
    public Collection<Person> findAll() {
        List<Person> res = new ArrayList<>();
        personRep.findAll().forEach(res::add);
        return res;
    }

    @Override
    public Optional<Person> findById(long id) {
        return personRep.findById(id);
    }

    @Override
    public Person save(Person person) {
        return personRep.save(person);
    }

    @Override
    public void delete(Person person) {
        personRep.delete(person);
    }

    @Override
    public Person findPersonByUsername(String name) {
        return personRep.findPersonByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = personRep.findPersonByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }
}

