package ru.javamentor.RestService.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.RestService.entity.Role;
import ru.javamentor.RestService.entity.User;
import ru.javamentor.RestService.exception.UserNotFoundException;
import ru.javamentor.RestService.response.UserSuccessResponse;
import ru.javamentor.RestService.service.RoleService;
import ru.javamentor.RestService.service.UserService;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class UserRestController {
    private UserService userService;
    private RoleService roleService;

    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/users/list")
    public List<User> getUserList() {
        return userService.listAll();
    }

    @GetMapping(value = "/users/{userId}")
    public User getUser(@PathVariable("userId") long userId) {
        if (userService.get(userId) == null || (userId < 0)) {
            throw new UserNotFoundException("User id not found - " + userId);
        }
        return userService.get(userId);
    }

    @GetMapping(value = "/users")
    public User getUserByName(@RequestParam("username") String username) {
        final User user = (User) userService.loadUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found, username: " + username);
        }
        return user;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserSuccessResponse> addUser(@RequestBody User user) {
        setRoles(user);
        user.setId(null);
        userService.save(user);
        UserSuccessResponse successResponse = new UserSuccessResponse(
                HttpStatus.OK.value(),
                "User " + user.getUsername() + " successfully added",
                System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/users")
    public ResponseEntity<UserSuccessResponse> updateUser(@RequestBody User user) {
        if (userService.get(user.getId()) == null || (user.getId() < 0)) {
            throw new UserNotFoundException("User id not found - " + user.getId());
        }
        setRoles(user);
        userService.update(user);
        UserSuccessResponse successResponse = new UserSuccessResponse(
                HttpStatus.OK.value(),
                "User successfully updated",
                System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity<UserSuccessResponse> deleteUser(@PathVariable long userId) {
        if (userService.get(userId) == null || (userId < 0)) {
            throw new UserNotFoundException("User id not found - " + userId);
        }
        userService.delete(userId);
        UserSuccessResponse successResponse = new UserSuccessResponse(
                HttpStatus.OK.value(),
                "User successfully deleted id - " + userId,
                System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);

    }

    private void setRoles(User user) {
        final Set<Role> roleSet = user.getRoles();
        for (Role tmpRole : roleSet) {
            tmpRole.setId(roleService.findByRole(tmpRole.getRole()).getId());
        }
    }
}
