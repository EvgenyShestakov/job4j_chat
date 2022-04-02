package ru.job4j.chat.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PersonDTO {
    @NotNull(message = "Id must be non null")
    private int id;

    @NotBlank(message = "Username must be not empty")
    private String username;

    @NotBlank(message = "Password must be not empty")
    private String password;

    @Email(message = "Email isn't valid")
    private String email;

    @Min(value = 1, message = "Year must be more than 0")
    private int roleID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
