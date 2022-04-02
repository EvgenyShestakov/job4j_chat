package ru.job4j.chat.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessageDTO {
    @NotNull(message = "Id must be non null")
    private int id;

    @NotBlank(message = "Body must be not empty")
    private String body;

    @Min(value = 1, message = "Year must be more than 0")
    private int personId;

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

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
