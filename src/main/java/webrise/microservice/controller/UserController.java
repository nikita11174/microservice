package webrise.microservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webrise.microservice.model.User;
import webrise.microservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Получен запрос на создание пользователя: {}", user);
        User created = userService.createUser(user);
        log.info("Пользователь успешно создан: {}", created);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.info("Получен запрос на получение пользователя с id: {}", id);
        return userService.getUser(id)
                .map(user -> {
                    log.info("Пользователь найден: {}", user);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> {
                    log.warn("Пользователь с id {} не найден", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя с id {}: {}", id, user);
        try {
            User updated = userService.updateUser(id, user);
            log.info("Пользователь успешно обновлён: {}", updated);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.error("Ошибка при обновлении пользователя с id {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Получен запрос на получение всех пользователей");
        List<User> users = userService.getAllUsers();
        log.info("Найдено {} пользователей", users.size());
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Получен запрос на удаление пользователя с id: {}", id);
        userService.deleteUser(id);
        log.info("Пользователь с id {} успешно удалён", id);
        return ResponseEntity.noContent().build();
    }
}
