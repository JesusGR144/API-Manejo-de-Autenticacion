package com.jjgr.store_demo.user;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@PreAuthorize("denyAll()")
public class UserController {
    private final UserService userService;

    // Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("getAll")
    public List<UserEntity> getAll() {
        return userService.getUsers();
    }

    // POST
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("addUser")
    public void addUser(@RequestBody UserEntity user) {
        userService.addUser(user);
    }

    // DELETE
    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        // userService.deleteUser();
        userService.deleteUser(userId);
    }
}
