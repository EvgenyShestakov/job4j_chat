package ru.job4j.chat.model;

import ru.job4j.chat.exeption.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null", groups = Operation.OnUpdate.class)
    private int id;

    @NotBlank(message = "Body must be not empty")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id")
    @NotNull(message = "Person must be non null", groups = Operation.OnCreate.class)
    private Person person;

    public Message() {
    }

    public Message(int id, String body, Person person) {
        this.id = id;
        this.body = body;
        this.person = person;
    }

    public Message(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
