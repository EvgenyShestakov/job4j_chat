package ru.job4j.chat.service;

import ru.job4j.chat.model.Role;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(int id);
}
