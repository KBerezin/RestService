package ru.javamentor.RestService.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.RestService.entity.Role;
import ru.javamentor.RestService.repository.RoleRepository;

import java.util.List;


@Service
public class RoleService {

    private final RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void save(Role user) {
        repo.save(user);
    }

    public List<Role> listAll() {
        return repo.findAll();
    }

    public Role get(Long id) {
        return repo.findById(id).get();
    }

    public Role findByRole(String role) {
        return repo.findByRole(role);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
