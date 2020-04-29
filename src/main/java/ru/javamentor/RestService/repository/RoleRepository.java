package ru.javamentor.RestService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.RestService.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole (String role);
}
