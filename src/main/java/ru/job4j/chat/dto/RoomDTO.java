package ru.job4j.chat.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RoomDTO {
    @NotNull(message = "Id must be non null")
    private int id;

    @NotBlank(message = "Name must be not empty")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
