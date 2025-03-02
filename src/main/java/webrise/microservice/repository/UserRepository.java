package webrise.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webrise.microservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}