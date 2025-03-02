package webrise.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webrise.microservice.model.Subscription;
import webrise.microservice.model.User;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUser(User user);

    boolean existsByUserAndServiceName(User user, String serviceName);

    @Query(value = """
            SELECT s.service_name, COUNT(*) AS cnt
            FROM subscriptions s
            GROUP BY s.service_name
            ORDER BY cnt DESC
            LIMIT 3
            """, nativeQuery = true)
    List<Object[]> findTopSubscriptionsNative();
}
