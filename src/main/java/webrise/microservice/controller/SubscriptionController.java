package webrise.microservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webrise.microservice.model.Subscription;
import webrise.microservice.service.SubscriptionService;

import java.util.List;

@RestController
@Slf4j
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<Subscription> addSubscription(@PathVariable("id") Long userId, @RequestBody Subscription subscription) {
        log.info("Получен запрос на добавление подписки '{}' для пользователя с id: {}", subscription.getServiceName(), userId);
        try {
            Subscription created = subscriptionService.addSubscription(userId, subscription);
            log.info("Подписка '{}' успешно добавлена для пользователя с id: {}", created.getServiceName(), userId);
            return ResponseEntity.ok(created);
        } catch (RuntimeException ex) {
            log.error("Ошибка при добавлении подписки '{}' для пользователя с id {}: {}",
                    subscription.getServiceName(), userId, ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users/{id}/subscriptions")
    public ResponseEntity<List<Subscription>> getSubscriptions(@PathVariable("id") Long userId) {
        log.info("Получен запрос на получение подписок для пользователя с id: {}", userId);
        List<Subscription> subscriptions = subscriptionService.getSubscriptions(userId);
        log.info("Найдено {} подписок для пользователя с id: {}", subscriptions.size(), userId);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/users/{id}/subscriptions/{sub_id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable("id") Long userId, @PathVariable("sub_id") Long subscriptionId) {
        log.info("Получен запрос на удаление подписки с id {} для пользователя с id: {}", subscriptionId, userId);
        try {
            subscriptionService.deleteSubscription(userId, subscriptionId);
            log.info("Подписка с id {} успешно удалена для пользователя с id: {}", subscriptionId, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            log.error("Ошибка при удалении подписки с id {} для пользователя с id {}: {}", subscriptionId, userId, ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<Object[]>> getTopSubscriptions() {
        log.info("Получен запрос на получение ТОП-3 популярных подписок");
        List<Object[]> topSubscriptions = subscriptionService.getTopSubscriptions();
        log.info("ТОП-3 подписок: {}", topSubscriptions);
        return ResponseEntity.ok(topSubscriptions);
    }
}
