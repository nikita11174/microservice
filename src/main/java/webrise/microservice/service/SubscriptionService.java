package webrise.microservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webrise.microservice.model.Subscription;
import webrise.microservice.model.User;
import webrise.microservice.repository.SubscriptionRepository;
import webrise.microservice.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public Subscription addSubscription(Long userId, Subscription subscription) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        boolean exists = subscriptionRepository.existsByUserAndServiceName(user, subscription.getServiceName());
        if (exists) throw new RuntimeException("Подписка уже существует для данного пользователя");
        subscription.setUser(user);
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getSubscriptions(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return subscriptionRepository.findByUser(user);
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new RuntimeException("Подписка не найдена"));
        if (!subscription.getUser().getId().equals(userId)) {
            throw new RuntimeException("Подписка не принадлежит пользователю с ID " + userId);
        }
        subscriptionRepository.delete(subscription);
    }

    public List<Object[]> getTopSubscriptions() {
        return subscriptionRepository.findTopSubscriptionsNative();
    }
}
