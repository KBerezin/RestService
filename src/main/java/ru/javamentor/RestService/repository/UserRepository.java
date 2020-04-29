package ru.javamentor.RestService.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.RestService.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
