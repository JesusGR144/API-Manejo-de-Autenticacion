package com.jjgr.store_demo.user;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    //Constructor
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("getAll")
    public List<User> getAll() {
        return userService.getUsers();
    }

    //POST
    @PostMapping("addUser")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    //DELETE
    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        // userService.deleteUser();
        userService.deleteUser(userId);
    }
}
