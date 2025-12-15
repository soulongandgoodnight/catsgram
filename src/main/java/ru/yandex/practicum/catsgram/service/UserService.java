package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();  // Хранилище здесь

    public Collection<User> findAll() {
        return users.values();
    }

    public User create(User newUser) {
        if (newUser.getEmail() == null || newUser.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        if (emailExists(newUser.getEmail())) {
            throw new ConditionsNotMetException("Этот имейл уже используется");
        }
        newUser.setId(getNextId());
        newUser.setRegistrationDate(Instant.now());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public User update(User updatedUser) {
        if (updatedUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (!users.containsKey(updatedUser.getId())) {
            throw new ConditionsNotMetException("Пользователь не найден");
        }
        User existingUser = users.get(updatedUser.getId());
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
            if (!updatedUser.getEmail().equals(existingUser.getEmail()) && emailExists(updatedUser.getEmail())) {
                throw new ConditionsNotMetException("Этот имейл уже используется");
            }
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank()) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        return existingUser;
    }

    public User findById(long userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        return user;
    }

    public Optional<User> findUserById(long id) {
        return users.containsKey(id) ? Optional.of(users.get(id)) : Optional.empty();
    }

    private boolean emailExists(String email) {
        return users.values().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
